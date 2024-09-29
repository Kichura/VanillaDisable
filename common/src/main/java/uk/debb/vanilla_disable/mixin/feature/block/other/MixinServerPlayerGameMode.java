/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ServerPlayerGameMode.class)
public abstract class MixinServerPlayerGameMode {
    @Shadow
    protected ServerLevel level;

    @WrapMethod(method = "destroyBlock")
    private boolean vanillaDisable$destroyBlock(BlockPos pos, Operation<Boolean> original) {
        String block = DataUtils.getKeyFromBlockRegistry(
                this.level.getBlockState(pos).getBlock());
        if (SqlManager.getBoolean("blocks", block, "can_break")) {
            original.call(pos);
        }
        return false;
    }
}
