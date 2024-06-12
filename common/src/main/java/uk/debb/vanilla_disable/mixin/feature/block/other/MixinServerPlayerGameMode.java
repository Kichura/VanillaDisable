/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ServerPlayerGameMode.class)
public abstract class MixinServerPlayerGameMode {
    @Shadow
    protected ServerLevel level;

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        String block = DataUtils.getKeyFromBlockRegistry(
                this.level.getBlockState(pos).getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_break")) {
            cir.setReturnValue(false);
        }
    }
}
