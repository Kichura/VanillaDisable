/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.data;

import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import uk.debb.vanilla_disable.Constants;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static uk.debb.vanilla_disable.config.data.DataDefinitions.*;

public class SqlManager {
    public static final Properties properties = new Properties();
    public static final Object2ObjectMap<String, Object2BooleanMap<String>> worldgenMaps = new Object2ObjectOpenHashMap<>() {{
        put("biomes", new Object2BooleanOpenHashMap<>());
        put("placed_features", new Object2BooleanOpenHashMap<>());
        put("structures", new Object2BooleanOpenHashMap<>());
    }};
    public static final Object2ObjectMap<String, ObjectList<String>> legacyGameruleMap = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<String, Object> memo = new Object2ObjectOpenHashMap<>();
    public static File tomlFile;
    public static File propertiesFile;
    private static Connection connection;
    private static Statement statement;
    private static String PATH;

    private static void generateData(boolean create, String tables) {
        if (create) {
            colData.forEach((table, value) -> {
                try {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + "(id CLOB NOT NULL, " +
                            value.values().stream().flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                                    .entrySet().stream().map(entry -> "\"" + entry.getKey() + "\" " + entry.getValue().left())
                                    .collect(Collectors.joining(", ")) + ");");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        rowData.forEach((table, data) -> {
            if (tables.equals("*") || tables.equals(table)) {
                data.forEach((key, value) -> {
                    try {
                        statement.executeUpdate("INSERT INTO " + table + " (id, \"" + String.join("\", \"", value.keySet()) + "\") VALUES ('" + key + "', " + String.join(", ", value.values()) + ");");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    public static void handleDatabase() {
        PATH = server.getWorldPath(LevelResource.ROOT) + "/vanilla_disable_command.sql";

        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:vd");
            statement = connection.createStatement();

            generateData(true, "*");

            if (!new File(PATH).exists()) {
                if (!new File(PATH).createNewFile()) {
                    throw new RuntimeException("Could not create file " + PATH);
                }
            }

            File directory = server.getWorldPath(LevelResource.ROOT).toFile();
            tomlFile = new File(directory, "vanilla_disable_worldgen.toml");
            propertiesFile = new File(directory, "vanilla_disable_worldgen.properties");
            if (tomlFile.exists()) {
                convertToml();
            } else if (propertiesFile.exists()) {
                convertProperties();
            }

            worldgenMaps.forEach((table, map) -> {
                if (map.isEmpty()) return;
                if (map.values().stream().noneMatch(val -> val)) {
                    writeToFile("UPDATE " + table + " SET \"enabled\" = false;");
                } else {
                    map.forEach((key, value) -> {
                        if (value) return;
                        writeToFile("UPDATE " + table + " SET \"enabled\" = false WHERE id = '" + key + "';");
                    });
                }
            });

            worldgenMaps.forEach((table, map) -> map.clear());

            Scanner scanner = new Scanner(new File(PATH));
            while (scanner.hasNext()) {
                statement.execute(scanner.nextLine());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        legacyGameruleMap.forEach((rule, values) -> {
            if (rowData.get("misc").get(rule).get(values.getFirst()).equals(values.get(1))) return;
            setValues("misc", rule, values.getFirst(), values.get(1), false, null, SetType.ONE);
        });
        legacyGameruleMap.clear();
    }

    public static void convertToml() {
        if (!tomlFile.renameTo(propertiesFile)) {
            Constants.LOG.error("Unable to convert legacy .toml worldgen config to .properties format.");
            return;
        }

        try {
            String[] lines = FileUtils.readFileToString(propertiesFile, Charset.defaultCharset()).split("\n");
            Object2ObjectMap<String, StringBuilder> sections = new Object2ObjectOpenHashMap<>();

            String current = "";
            for (String line : lines) {
                if (line.startsWith("[")) {
                    current = line.substring(1, line.length() - 1);
                    sections.put(current, new StringBuilder());
                } else {
                    sections.get(current).append(line.replace("  ", current + ".")).append("\n");
                }
            }

            try (FileWriter writer = new FileWriter(propertiesFile)) {
                sections.values().forEach(content -> {
                    try {
                        writer.write(content.toString());
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                });
            } catch (IOException e) {
                throw new IOException(e);
            }
        } catch (IOException e) {
            Constants.LOG.error("Unable to convert legacy .toml worldgen config to .properties format.");
        }
    }

    public static void convertProperties() {
        try (FileInputStream in = new FileInputStream(propertiesFile)) {
            properties.load(in);
        } catch (IOException e) {
            Constants.LOG.error("Unable to load worldgen config.", e);
        }

        properties.forEach((key, value) -> {
            if (value == "false") {
                if (key.toString().startsWith("biomes.")) {
                    if (key.toString().contains("end_spike_cage")) {
                        writeToFile("UPDATE placed_features SET \"enabled\" = false WHERE id = 'minecraft_unofficial:end_spike_cage';");
                    } else {
                        writeToFile("UPDATE biomes SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(7) + "';");
                    }
                } else if (key.toString().startsWith("structures.")) {
                    writeToFile("UPDATE structures SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(11) + "';");
                } else if (key.toString().startsWith("placed_features.")) {
                    writeToFile("UPDATE placed_features SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(16) + "';");
                }
            }
        });

        if (!propertiesFile.delete()) {
            Constants.LOG.error("Unable to delete legacy .properties worldgen config.");
        }
    }

    public static void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isConnectionNull() {
        return connection == null;
    }

    public static void writeToFile(String command) {
        try {
            FileWriter writer = new FileWriter(PATH, true);
            writer.write(command + "\n");
            writer.close();
        } catch (IOException ignored) {
        }
    }

    public static void setValues(String table, @Nullable String row, String column, String value, boolean isString, @Nullable String pattern, SetType setType) {
        if (table.isEmpty() || column.isEmpty() || value.isEmpty() || (setType.equals(SetType.ONE) && (row == null || row.isEmpty())) || (setType.equals(SetType.MATCHING) && (pattern == null || pattern.isEmpty())))
            return;
        memo.clear();
        if (isString) {
            value = "'" + value + "'";
        }
        if (pattern != null && (pattern.contains(";") || pattern.contains("SELECT") || pattern.contains("ALTER"))) {
            throw new RuntimeException("SQL injection attempted. Command not executed.");
        }
        try {
            String query = switch (setType) {
                case ONE -> "UPDATE " + table + " SET \"" + column + "\" = " + value + " WHERE id = '" + row + "';";
                case MATCHING ->
                        "UPDATE " + table + " SET \"" + column + "\" = " + value + " WHERE \"" + column + "\" IS NOT NULL AND id LIKE '" + pattern + "';";
                case ALL ->
                        "UPDATE " + table + " SET \"" + column + "\" = " + value + " WHERE \"" + column + "\" IS NOT NULL;";
            };
            statement.executeUpdate(query);
            writeToFile(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValue(String table, String row, String column, DataType dataType) {
        try {
            Object value;
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT \"" + column + "\" FROM " + table + " WHERE id = '" + row + "';");
            resultSet.next();
            switch (dataType) {
                case BOOLEAN -> value = resultSet.getBoolean(column);
                case INTEGER -> value = resultSet.getInt(column);
                case REAL -> value = resultSet.getDouble(column);
                case CLOB -> value = resultSet.getString(column);
                default -> throw new IllegalStateException("Unexpected value: " + dataType);
            }
            resultSet.close();
            return value;
        } catch (SQLException | NullPointerException ignored) {
        }
        return switch (dataType) {
            case BOOLEAN -> Boolean.parseBoolean(rowData.get(table).get(row).get(column));
            case INTEGER -> Integer.parseInt(rowData.get(table).get(row).get(column));
            case REAL -> Double.parseDouble(rowData.get(table).get(row).get(column));
            case CLOB -> rowData.get(table).get(row).get(column);
        };
    }

    private static Object getCachedValue(String table, String row, String column, DataType dataType) {
        String cacheKey = dataType + "-" + table + "-" + row + "-" + column;
        if (memo.get(cacheKey) != null) {
            return memo.get(cacheKey);
        }
        Object value = getValue(table, row, column, dataType);
        memo.put(cacheKey, value);
        return value;
    }

    public static boolean getBoolean(String table, String row, String column) {
        return (boolean) getCachedValue(table, row, column, DataType.BOOLEAN);
    }

    public static int getInt(String table, String row, String column) {
        return (int) getCachedValue(table, row, column, DataType.INTEGER);
    }

    public static double getDouble(String table, String row, String column) {
        return (double) getCachedValue(table, row, column, DataType.REAL);
    }

    public static String getString(String table, String row, String column) {
        return (String) getCachedValue(table, row, column, DataType.CLOB);
    }

    private static List<ItemStack> getRawBreedingItems(String row) {
        ObjectSet<Item> items = new ObjectArraySet<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM entities WHERE id = '" + row + "';");
            if (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    if (columnName.startsWith("can_breed_with_")) {
                        if (resultSet.getBoolean(columnName)) {
                            items.add(Objects.requireNonNull(itemRegistry.get(ResourceLocation.bySeparator(
                                    columnName.replace("can_breed_with_", "minecraft:"), ':'))));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (items.contains(Items.CARROT)) {
            items.add(Items.CARROT_ON_A_STICK);
        }
        if (items.contains(Items.WARPED_FUNGUS)) {
            items.add(Items.WARPED_FUNGUS_ON_A_STICK);
        }
        return items.stream().map(ItemStack::new).toList();
    }

    @SuppressWarnings("unchecked")
    public static List<ItemStack> getBreedingItems(String row) {
        String cacheKey = "getBreedingItems-" + row;
        if (memo.get(cacheKey) instanceof List<?> itemStack) {
            return (List<ItemStack>) itemStack;
        }
        List<ItemStack> itemStack = getRawBreedingItems(row);
        memo.put(cacheKey, itemStack);
        return itemStack;
    }

    public static void resetAll() {
        memo.clear();
        if (!new File(PATH).exists()) return;
        if (!new File(PATH).delete()) {
            throw new RuntimeException("Could not delete file " + PATH);
        }
        closeConnection();
        handleDatabase();
    }

    public static void resetOne(String db) {
        memo.clear();
        if (!new File(PATH).exists()) return;
        try {
            List<String> lines = FileUtils.readLines(new File(PATH), Charset.defaultCharset())
                    .stream().filter(line -> !line.contains("UPDATE " + db)).toList();
            FileUtils.writeLines(new File(PATH), lines, false);

            statement.executeUpdate("DELETE FROM " + db + ";");
            generateData(false, db);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetPartial(String db, ObjectSet<String> cols) {
        memo.clear();
        if (!new File(PATH).exists()) return;
        try {
            List<String> lines = FileUtils.readLines(new File(PATH), Charset.defaultCharset())
                    .stream().filter(line -> {
                        if (line.contains("UPDATE " + db)) {
                            return cols.stream().noneMatch(line::contains);
                        }
                        return true;
                    }).toList();
            FileUtils.writeLines(new File(PATH), lines, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeConnection();
        handleDatabase();
    }

    public static void undo(int count) {
        memo.clear();
        if (!new File(PATH).exists()) return;
        try {
            List<String> lines = FileUtils.readLines(new File(PATH), Charset.defaultCharset());
            for (int i = 0; i < count; i++) {
                if (lines.isEmpty()) break;
                lines.removeLast();
            }
            FileUtils.writeLines(new File(PATH), lines, false);

            closeConnection();
            handleDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
