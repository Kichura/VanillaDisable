/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util.gamerule;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EditGameRulesScreen.RuleList.class)
public abstract class MixinEditGameRulesScreenRuleList {
    @ModifyExpressionValue(
            method = "lambda$new$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/MutableComponent;withStyle([Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"
            )
    )
    private MutableComponent vanillaDisable$withStyle(MutableComponent original) {
        return original.getString().contains("Vanilla Disable") ? original.withStyle(ChatFormatting.DARK_GREEN) : original;
    }
}
