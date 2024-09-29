/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.conversions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.monster.Skeleton;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Skeleton.class)
public abstract class MixinSkeleton {
    @WrapMethod(method = "doFreezeConversion")
    private void vanillaDisable$doFreezeConversion(Operation<Void> original) {
        if (SqlManager.getBoolean("entities", "minecraft:stray", "can_be_converted_to")) {
            original.call();
        }
    }
}
