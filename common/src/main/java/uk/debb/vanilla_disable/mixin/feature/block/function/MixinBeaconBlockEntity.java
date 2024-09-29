/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BeaconBlockEntity.class)
public abstract class MixinBeaconBlockEntity {
    @WrapMethod(method = "applyEffects")
    private static void vanillaDisable$applyEffects(Level level, BlockPos pos, int beaconLevel, @Nullable Holder<MobEffect> primaryEffect, @Nullable Holder<MobEffect> secondaryEffect, Operation<Void> original) {
        if (SqlManager.getBoolean("blocks", "minecraft:beacon", "works")) {
            original.call(level, pos, beaconLevel, primaryEffect, secondaryEffect);
        }
    }
}
