/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.fluid;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(WaterFluid.class)
public abstract class MixinWaterFluid {
    @ModifyReturnValue(method = "getDropOff", at = @At("RETURN"))
    private int vanillaDisable$getDropOff(int original, LevelReader level) {
        if (level instanceof Level) {
            if (level.dimensionType().ultraWarm()) {
                return SqlManager.getBoolean("blocks", "minecraft:water", "fluid_reaches_far_in_nether") ? 1 : 2;
            } else {
                return SqlManager.getBoolean("blocks", "minecraft:water", "fluid_reaches_far") ? 1 : 2;
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "getTickDelay", at = @At("RETURN"))
    private int vanillaDisable$getTickDelay(int original, LevelReader level) {
        if (level instanceof Level) {
            if (level.dimensionType().ultraWarm()) {
                return SqlManager.getInt("blocks", "minecraft:water", "fluid_speed_in_nether");
            } else {
                return SqlManager.getInt("blocks", "minecraft:water", "fluid_speed");
            }
        }
        return original;
    }
}