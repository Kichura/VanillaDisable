/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.command;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.brigadier.ParseResults;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Commands.class)
public abstract class MixinCommands {
    @WrapMethod(method = "performCommand")
    private void vanillaDisable$performCommand(ParseResults<CommandSourceStack> parseResults, String command, Operation<Void> original) {
        if (DataDefinitions.rowData.get("commands").containsKey("/" + command.split(" ")[0]) &&
                !SqlManager.getBoolean("commands", "/" + command.split(" ")[0], "enabled")) {
            DataDefinitions.server.getPlayerList().broadcastSystemMessage(Component.translatable("vd.commands.disabled.by.vd").withStyle(ChatFormatting.RED), false);
            return;
        }
        original.call(parseResults, command);
    }
}
