/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.data;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class VDCommand {
    private final CommandDispatcher<CommandSourceStack> dispatcher;

    public VDCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void init() {
        LiteralArgumentBuilder<CommandSourceStack> ruleBuilder = literal("rule");
        DataDefinitions.colData.keySet().forEach(table -> ruleBuilder.then(getTableCommand(table)));
        this.dispatcher.register(literal("vd").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(getResetCommand())
                .then(ruleBuilder)
        );
    }

    private LiteralArgumentBuilder<CommandSourceStack> getResetCommand() {
        LiteralArgumentBuilder<CommandSourceStack> resetDBBuilder = literal("reset");
        resetDBBuilder.then(literal("all").executes(context -> {
            SqlManager.resetAll();
            context.getSource().sendSuccess(
                    () -> Component.translatable("vd.command.all_db_reset"),
                    false
            );
            return 1;
        }));

        DataDefinitions.colData.forEach((table, tableData) -> {
            LiteralArgumentBuilder<CommandSourceStack> tableBuilder = literal(DataDefinitions.singularMap.get(table)).executes(context -> {
                SqlManager.resetOne(table);
                context.getSource().sendSuccess(
                        () -> Component.translatable("vd.command.one_db_reset", table),
                        false
                );
                return 1;
            });

            tableData.forEach((group, groupData) -> tableBuilder.then(literal(group).executes(context -> {
                SqlManager.resetPartial(table, groupData.keySet());
                context.getSource().sendSuccess(
                        () -> Component.translatable("vd.command.one_group_reset", group, table),
                        false
                );
                return 1;
            })));

            resetDBBuilder.then(tableBuilder);
        });

        return resetDBBuilder;
    }

    private LiteralArgumentBuilder<CommandSourceStack> getTableCommand(String table) {
        LiteralArgumentBuilder<CommandSourceStack> overallBuilder = literal(DataDefinitions.singularMap.get(table));

        DataDefinitions.rowData.get(table).forEach((row, rowData) -> {
            LiteralArgumentBuilder<CommandSourceStack> rowBuilder = literal(row);
            DataDefinitions.colData.get(table).forEach((group, colData) -> {
                Object2ObjectMap<String, Component> properties = colData.entrySet().stream().filter(entry -> rowData.containsKey(entry.getKey()))
                        .collect(Object2ObjectOpenHashMap::new, (m, entry) -> m.put(entry.getKey(), entry.getValue().right()), Object2ObjectOpenHashMap::putAll);
                if (properties.isEmpty()) return;
                LiteralArgumentBuilder<CommandSourceStack> groupBuilder = literal(group);
                properties.forEach((col, desc) ->
                        groupBuilder.then(getSingleColCommand(row, table, group, col, desc, SetType.ONE)));
                if (!DataDefinitions.differentDataTypes.contains(group)) {
                    groupBuilder.then(getAllColCommand(row, table, group, SetType.ONE));
                }
                rowBuilder.then(groupBuilder);
            });
            overallBuilder.then(rowBuilder);
        });

        LiteralArgumentBuilder<CommandSourceStack> allBuilder = literal("all");
        RequiredArgumentBuilder<CommandSourceStack, String> matchesBuilder = argument("pattern", StringArgumentType.string());
        DataDefinitions.colData.get(table).forEach((group, colData) -> {
            LiteralArgumentBuilder<CommandSourceStack> allGroupBuilder = literal(group);
            LiteralArgumentBuilder<CommandSourceStack> matchesGroupBuilder = literal(group);
            colData.forEach((col, desc) -> {
                allGroupBuilder.then(getSingleColCommand("all", table, group, col, desc.right(), SetType.ALL));
                matchesGroupBuilder.then(getSingleColCommand("matches", table, group, col, desc.right(), SetType.MATCHING));
            });
            if (!DataDefinitions.differentDataTypes.contains(group)) {
                allGroupBuilder.then(getAllColCommand("all", table, group, SetType.ALL));
                matchesGroupBuilder.then(getAllColCommand("matches", table, group, SetType.MATCHING));
            }
            allBuilder.then(allGroupBuilder);
            matchesBuilder.then(matchesGroupBuilder);
        });
        overallBuilder.then(allBuilder);
        overallBuilder.then(literal("matches").then(matchesBuilder));

        return overallBuilder;
    }

    private LiteralArgumentBuilder<CommandSourceStack> getSingleColCommand(String row, String table, String group, String col, Component desc, SetType setType) {
        LiteralArgumentBuilder<CommandSourceStack> builder = literal(col);
        DataType type = DataDefinitions.colData.get(table).get(group).get(col).left();
        builder.executes(context -> {
            if (setType.equals(SetType.ONE)) {
                String value = switch (type) {
                    case BOOLEAN -> String.valueOf(SqlManager.getBoolean(table, row, col));
                    case INTEGER -> String.valueOf(SqlManager.getInt(table, row, col));
                    case REAL -> String.valueOf(SqlManager.getDouble(table, row, col));
                    case CLOB -> String.valueOf(SqlManager.getString(table, row, col));
                };
                context.getSource().sendSuccess(
                        () -> desc,
                        false
                );
                context.getSource().sendSuccess(
                        () -> Component.translatable("vd.command.current_value", value),
                        false
                );
            }
            context.getSource().sendSuccess(
                    () -> Component.translatable("vd.command.default_value", DataDefinitions.rowData.get(table).get(row).get(col).replace("'", "")),
                    false
            );
            return 1;
        });
        if (DataDefinitions.stringColSuggestions.containsKey(col)) {
            List<String> options = DataDefinitions.stringColSuggestions.get(col);
            builder.then(
                    argument("value", StringArgumentType.word()).suggests((ctx, b) -> SharedSuggestionProvider.suggest(options, b)).executes(context -> {
                        String value = StringArgumentType.getString(context, "value");
                        if (!options.contains(value)) {
                            context.getSource().sendFailure(
                                    Component.translatable("vd.command.invalid_value")
                            );
                            return 0;
                        }
                        switch (setType) {
                            case ONE -> SqlManager.setValues(table, row, col, value, true, null, SetType.ONE);
                            case MATCHING ->
                                    SqlManager.setValues(table, null, col, value, true, StringArgumentType.getString(context, "pattern"), SetType.MATCHING);
                            case ALL -> SqlManager.setValues(table, null, col, value, true, null, SetType.ALL);
                        }
                        context.getSource().sendSuccess(
                                () -> Component.translatable("vd.command.successfully_set_value", value),
                                false
                        );
                        return 1;
                    })
            );
        } else {
            builder.then(
                    argument("value", getArgumentType(table, group, col)).executes(context -> {
                        String value = getArgumentValue(table, group, col, context);
                        setValues(row, table, col, type, context, value, setType);
                        context.getSource().sendSuccess(
                                () -> Component.translatable("vd.command.successfully_set_value", value),
                                false
                        );
                        return 1;
                    })
            );
        }
        return builder;
    }

    private LiteralArgumentBuilder<CommandSourceStack> getAllColCommand(String row, String table, String group, SetType setType) {
        String firstCol = DataDefinitions.colData.get(table).get(group).keySet().iterator().next();
        DataType type = DataDefinitions.colData.get(table).get(group).get(firstCol).left();
        return literal("all").then(argument("value", getArgumentType(table, group, firstCol)).executes(context -> {
            String value = getArgumentValue(table, group, firstCol, context);
            DataDefinitions.colData.get(table).get(group).keySet().forEach(col ->
                    setValues(row, table, col, type, context, value, setType));
            context.getSource().sendSuccess(
                    () -> Component.translatable("vd.command.successfully_set_all_properties", group, value),
                    false
            );
            return 1;
        }));
    }

    private ArgumentType<?> getArgumentType(String table, String group, String col) {
        DataType type = DataDefinitions.colData.get(table).get(group).get(col).left();
        return switch (type) {
            case BOOLEAN -> BoolArgumentType.bool();
            case INTEGER ->
                    IntegerArgumentType.integer(0, (int) DataDefinitions.numRowMaximums.getOrDefault(col, Integer.MAX_VALUE));
            case REAL ->
                    DoubleArgumentType.doubleArg(0.0, DataDefinitions.numRowMaximums.getOrDefault(col, Double.MAX_VALUE));
            case CLOB -> StringArgumentType.greedyString();
        };
    }

    private String getArgumentValue(String table, String group, String col, CommandContext<?> context) {
        DataType type = DataDefinitions.colData.get(table).get(group).get(col).left();
        return switch (type) {
            case BOOLEAN -> String.valueOf(BoolArgumentType.getBool(context, "value"));
            case INTEGER -> String.valueOf(IntegerArgumentType.getInteger(context, "value"));
            case REAL -> String.valueOf(DoubleArgumentType.getDouble(context, "value"));
            case CLOB -> StringArgumentType.getString(context, "value");
        };
    }

    private void setValues(String row, String table, String col, DataType type, CommandContext<CommandSourceStack> context, String value, SetType setType) {
        switch (setType) {
            case ONE -> SqlManager.setValues(table, row, col, value, type.equals(DataType.CLOB), null, SetType.ONE);
            case MATCHING ->
                    SqlManager.setValues(table, null, col, value, type.equals(DataType.CLOB), StringArgumentType.getString(context, "pattern"), SetType.MATCHING);
            case ALL -> SqlManager.setValues(table, null, col, value, type.equals(DataType.CLOB), null, SetType.ALL);
        }
    }
}
