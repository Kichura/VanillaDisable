/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.function;

import net.minecraft.world.entity.projectile.SpectralArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(SpectralArrow.class)
public abstract class MixinSpectralArrow {
    @Inject(method = "doPostHurtEffects", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$doPostHurtEffects(CallbackInfo ci) {
        if (!SqlManager.getBoolean("items", "minecraft:spectral_arrow", "works")) {
            ci.cancel();
        }
    }
}