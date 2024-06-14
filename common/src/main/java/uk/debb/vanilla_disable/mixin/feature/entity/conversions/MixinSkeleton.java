/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.conversions;

import net.minecraft.world.entity.monster.Skeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Skeleton.class)
public abstract class MixinSkeleton {
    @Inject(method = "doFreezeConversion", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$doFreezeConversion(CallbackInfo ci) {
        if (!SqlManager.getBoolean("entities", "minecraft:stray", "can_be_converted_to")) {
            ci.cancel();
        }
    }
}