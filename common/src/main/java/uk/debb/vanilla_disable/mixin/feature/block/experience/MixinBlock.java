/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.experience;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow
    protected abstract Block asBlock();

    @WrapMethod(method = "popExperience")
    private void vanillaDisable$popExperience(ServerLevel level, BlockPos pos, int amount, Operation<Void> original) {
        String block = DataUtils.getKeyFromBlockRegistry(this.asBlock());
        if (SqlManager.getBoolean("blocks", block, "can_drop_xp")) {
            original.call(level, pos, amount);
        }
    }
}
