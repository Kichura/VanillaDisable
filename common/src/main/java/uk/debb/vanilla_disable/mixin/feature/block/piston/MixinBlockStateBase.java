/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.piston;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @ModifyReturnValue(method = "getPistonPushReaction", at = @At("RETURN"))
    private PushReaction vanillaDisable$getPistonPushReaction(PushReaction original) {
        Block block = this.getBlock();
        if (SqlManager.isConnectionNull()) {
            if (block.equals(Blocks.OBSIDIAN) || block.equals(Blocks.CRYING_OBSIDIAN) || block.equals(Blocks.RESPAWN_ANCHOR) ||
                    block.equals(Blocks.REINFORCED_DEEPSLATE)) {
                return PushReaction.BLOCK;
            }
            return original;
        }
        String name = DataUtils.getKeyFromBlockRegistry(block);
        String reaction = SqlManager.getString("blocks", name, "push_behaviour");
        if (reaction == null) return block.defaultBlockState().pushReaction;
        return PushReaction.valueOf(reaction);
    }
}
