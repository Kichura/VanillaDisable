/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.interaction;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @WrapMethod(method = "useOn")
    private InteractionResult vanillaDisable$useOn(UseOnContext context, Operation<InteractionResult> original) {
        Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
        String block_str = DataUtils.getKeyFromBlockRegistry(block);
        if (!SqlManager.getBoolean("blocks", block_str, "can_interact")) {
            return InteractionResult.FAIL;
        }
        return original.call(context);
    }
}
