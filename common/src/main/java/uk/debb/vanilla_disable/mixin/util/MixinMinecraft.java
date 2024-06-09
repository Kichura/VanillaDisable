/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.gui.CommandConfigScreen;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void vanillaDisable$init(CallbackInfo ci) {
        CommandConfigScreen.keyMapping = new KeyMapping("vd.key.open_command_config", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, "vd.key.category");
        Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, CommandConfigScreen.keyMapping);
        KeyMapping.CATEGORY_SORT_ORDER.put("vd.key.category", KeyMapping.CATEGORY_SORT_ORDER.values().stream().max(Integer::compareTo).orElse(1) + 1);
    }
}
