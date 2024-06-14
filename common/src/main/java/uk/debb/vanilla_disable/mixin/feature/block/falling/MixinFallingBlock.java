/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.falling;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(FallingBlock.class)
public abstract class MixinFallingBlock {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        String name = DataUtils.getKeyFromBlockRegistry(state.getBlock());
        if (!SqlManager.getBoolean("blocks", name, "can_fall")) {
            ci.cancel();
        }
    }
}