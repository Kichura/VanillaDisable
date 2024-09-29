/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.function;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SpectralArrow;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(SpectralArrow.class)
public abstract class MixinSpectralArrow {
    @WrapMethod(method = "doPostHurtEffects")
    private void vanillaDisable$doPostHurtEffects(LivingEntity target, Operation<Void> original) {
        if (SqlManager.getBoolean("items", "minecraft:spectral_arrow", "works")) {
            original.call(target);
        }
    }
}
