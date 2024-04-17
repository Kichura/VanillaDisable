/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.worldgen.feature;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.data.worldgen.WorldgenDataHandler;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel {
    @Inject(method = "makeObsidianPlatform", at = @At("HEAD"), cancellable = true)
    private static void vanillaDisable$makeObsidianPlatform(CallbackInfo ci) {
        if (!WorldgenDataHandler.get("placed_features", "obsidian_platform")) {
            ci.cancel();
        }
    }
}
