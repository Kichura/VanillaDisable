/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import uk.debb.vanilla_disable.config.gui.WorldgenConfigScreen;

@Mixin(CreateWorldScreen.MoreTab.class)
public abstract class MixinCreateWorldScreenMoreTab {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void vanillaDisable$init(CreateWorldScreen createWorldScreen, CallbackInfo ci, @Local GridLayout.RowHelper rowHelper) {
        rowHelper.addChild(Button.builder(Component.translatable("vd.worldgen_config.button"), button ->
                Minecraft.getInstance().setScreen(new WorldgenConfigScreen(createWorldScreen))).width(210).build());
    }
}
