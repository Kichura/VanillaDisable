/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.redstone_timings;

import net.minecraft.world.level.block.ObserverBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ObserverBlock.class)
public abstract class MixinObserverBlock {
    @ModifyArg(
            method = "startSignal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"
            ),
            index = 2
    )
    private int vanillaDisable$scheduleTick1(int delay) {
        return SqlManager.getInt("blocks", "minecraft:observer", "redstone_delay");
    }

    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"
            )
    )
    private int vanillaDisable$scheduleTick2(int duration) {
        return SqlManager.getInt("blocks", "minecraft:observer", "redstone_duration");
    }
}
