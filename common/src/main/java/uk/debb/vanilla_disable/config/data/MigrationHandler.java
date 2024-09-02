package uk.debb.vanilla_disable.config.data;

import com.mojang.serialization.DynamicLike;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.commons.io.FileUtils;
import uk.debb.vanilla_disable.Constants;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

import static uk.debb.vanilla_disable.config.data.DataDefinitions.server;

public class MigrationHandler {
    private static final Properties properties = new Properties();
    public static DynamicLike<?> dynamic;
    private static File tomlFile;
    private static File propertiesFile;

    public static void migrateGamerules() {
        if (GameruleMigrationDefinitions.migrationData.isEmpty()) {
            GameruleMigrationDefinitions.init();
        }
        boolean v2Conversion = dynamic.get("damageEnabled").asString().result().isPresent();
        GameruleMigrationDefinitions.migrationData.forEach((table, data) -> data.forEach(holder -> {
                    if (!v2Conversion && !table.equals("misc")) return;
                    dynamic.get(holder.gameruleName).asString().result().ifPresent(s -> {
                        if (DataDefinitions.rowData.get(table).containsKey(holder.rowName)) {
                            if (DataDefinitions.rowData.get(table).get(holder.rowName).get(holder.columnNames.getFirst()).equals(s))
                                return;
                            holder.columnNames.forEach(columnName -> SqlManager.setValues(table, null, columnName, s, false, holder.rowName, SetType.MATCHING));
                        }
                    });
                }
        ));
    }

    public static void migrateWorldgen() {
        File directory = server.getWorldPath(LevelResource.ROOT).toFile();
        tomlFile = new File(directory, "vanilla_disable_worldgen.toml");
        propertiesFile = new File(directory, "vanilla_disable_worldgen.properties");
        if (tomlFile.exists()) {
            convertToml();
        } else if (propertiesFile.exists()) {
            convertProperties();
        }
    }

    private static void convertToml() {
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

    private static void convertProperties() {
        try (FileInputStream in = new FileInputStream(propertiesFile)) {
            properties.load(in);
        } catch (IOException e) {
            Constants.LOG.error("Unable to load worldgen config.", e);
        }

        properties.forEach((key, value) -> {
            if (value == "false") {
                if (key.toString().startsWith("biomes.")) {
                    if (key.toString().contains("end_spike_cage")) {
                        SqlManager.writeToFile("UPDATE placed_features SET \"enabled\" = false WHERE id = 'minecraft_unofficial:end_spike_cage';");
                    } else {
                        SqlManager.writeToFile("UPDATE biomes SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(7) + "';");
                    }
                } else if (key.toString().startsWith("structures.")) {
                    SqlManager.writeToFile("UPDATE structures SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(11) + "';");
                } else if (key.toString().startsWith("placed_features.")) {
                    SqlManager.writeToFile("UPDATE placed_features SET \"enabled\" = false WHERE id = 'minecraft:" + key.toString().substring(16) + "';");
                }
            }
        });

        if (!propertiesFile.delete()) {
            Constants.LOG.error("Unable to delete legacy .properties worldgen config.");
        }
    }
}
