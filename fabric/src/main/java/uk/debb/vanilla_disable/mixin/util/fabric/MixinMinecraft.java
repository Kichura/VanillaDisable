/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "destroy", at = @At(value = "HEAD"))
    private void destroy(CallbackInfo ci) {
        try {
            FileUtils.deleteDirectory(new File(FabricLoader.getInstance().getGameDir().toString() + "/resourcepacks/vdlangfile"));
        } catch (IOException ignored) {
        }
    }
}
