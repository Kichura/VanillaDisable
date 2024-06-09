/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.command;

import com.mojang.brigadier.ParseResults;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(Commands.class)
public abstract class MixinCommands {
    @Inject(method = "performCommand", at = @At(value = "HEAD"), cancellable = true)
    private void vanillaDisable$performCommand(ParseResults<CommandSourceStack> parseResults, String command, CallbackInfo ci) {
        if (!DataHandler.commands.containsKey("/" + command.split(" ")[0])) return;
        if (!DataHandler.getCachedBoolean("commands", "/" + command.split(" ")[0], "enabled")) {
            DataHandler.server.getPlayerList().broadcastSystemMessage(Component.translatable("vd.commands.disabled.by.vd").withStyle(ChatFormatting.RED), false);
            ci.cancel();
        }
    }
}
