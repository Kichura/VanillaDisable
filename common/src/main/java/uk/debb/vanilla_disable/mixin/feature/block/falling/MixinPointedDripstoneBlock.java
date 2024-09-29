/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.falling;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(PointedDripstoneBlock.class)
public abstract class MixinPointedDripstoneBlock {
    @WrapMethod(method = "spawnFallingStalactite")
    private static void vanillaDisable$spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos, Operation<Void> original) {
        if (SqlManager.getBoolean("blocks", "minecraft:pointed_dripstone", "can_fall")) {
            original.call(state, level, pos);
        }
    }
}
