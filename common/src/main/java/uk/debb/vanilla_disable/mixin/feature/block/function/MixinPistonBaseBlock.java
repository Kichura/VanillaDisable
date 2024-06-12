/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(PistonBaseBlock.class)
public abstract class MixinPistonBaseBlock {
    @Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param, CallbackInfoReturnable<Boolean> cir) {
        String type = DataUtils.getKeyFromBlockRegistry(state.getBlock());
        if (!SqlManager.getBoolean("blocks", type, "works")) {
            cir.setReturnValue(false);
        }
    }
}
