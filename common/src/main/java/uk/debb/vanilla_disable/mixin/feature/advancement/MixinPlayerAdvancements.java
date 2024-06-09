/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.advancement;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.commands.AdvancementCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(PlayerAdvancements.class)
public abstract class MixinPlayerAdvancements {
    @Inject(method = "award", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$award(AdvancementHolder advancementHolder, String string, CallbackInfoReturnable<Boolean> cir) {
        String adv = advancementHolder.id().toString();
        if (!adv.contains("recipe") && !DataHandler.getCachedBoolean("advancements", adv, "enabled")) {
            if (Thread.currentThread().getStackTrace()[5].getClassName().equals(AdvancementCommands.class.getName())) {
                DataHandler.server.getPlayerList().broadcastSystemMessage(Component.translatable("vd.advancements.disabled.by.vd").withStyle(ChatFormatting.RED), false);
            }
            cir.setReturnValue(false);
        }
    }
}
