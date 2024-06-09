/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.EndPlatformFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(EndPlatformFeature.class)
public abstract class MixinEndPlatformFeature {
    @Inject(method = "createEndPlatform", at = @At("HEAD"), cancellable = true)
    private static void vanillaDisable$createEndPlatform(ServerLevelAccessor serverLevelAccessor, BlockPos blockPos, boolean bl, CallbackInfo ci) {
        if (!DataHandler.getCachedBoolean("placed_features", "minecraft:end_platform", "enabled")) {
            ci.cancel();
        }
    }
}
