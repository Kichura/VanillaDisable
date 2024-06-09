/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(FireBlock.class)
public abstract class MixinFireBlock {
    @ModifyReturnValue(method = "getBurnOdds", at = @At("RETURN"))
    private int vanillaDisable$getBurnOdds(int original, BlockState state) {
        if (DataHandler.isConnectionNull()) return original;
        String block = DataHandler.getKeyFromBlockRegistry(state.getBlock());
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)
                ? 0 : DataHandler.getCachedInt("blocks", block, "burn_odds");
    }

    @ModifyReturnValue(method = "getIgniteOdds(Lnet/minecraft/world/level/block/state/BlockState;)I", at = @At("RETURN"))
    private int vanillaDisable$getIgniteOdds(int original, BlockState state) {
        if (DataHandler.isConnectionNull()) return original;
        String block = DataHandler.getKeyFromBlockRegistry(state.getBlock());
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)
                ? 0 : DataHandler.getCachedInt("blocks", block, "ignite_odds");
    }
}
