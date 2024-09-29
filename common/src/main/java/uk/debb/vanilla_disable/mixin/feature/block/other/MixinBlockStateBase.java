/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @ModifyReturnValue(method = "ignitedByLava", at = @At("RETURN"))
    private boolean vanillaDisable$ignitedByLava(boolean original) {
        if (SqlManager.isConnectionNull()) return original;
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        return SqlManager.getBoolean("blocks", block, "ignited_by_lava");
    }

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float vanillaDisable$getDestroySpeed(float original) {
        if (SqlManager.isConnectionNull()) return original;
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        return (float) SqlManager.getDouble("blocks", block, "destroy_speed");
    }

    @ModifyReturnValue(method = "requiresCorrectToolForDrops", at = @At("RETURN"))
    private boolean vanillaDisable$requiresCorrectToolForDrops(boolean original) {
        if (SqlManager.isConnectionNull()) return original;
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        return SqlManager.getBoolean("blocks", block, "requires_correct_tool_for_drops");
    }
}
