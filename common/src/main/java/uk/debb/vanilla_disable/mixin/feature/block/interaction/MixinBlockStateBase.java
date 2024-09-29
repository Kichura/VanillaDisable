/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.interaction;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @WrapMethod(method = "useWithoutItem")
    private InteractionResult vanillaDisable$useWithoutItem(Level level, Player player, BlockHitResult hitResult, Operation<InteractionResult> original) {
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        if (SqlManager.getBoolean("blocks", block, "can_interact")) {
            return original.call(level, player, hitResult);
        }
        return InteractionResult.FAIL;
    }

    @WrapMethod(method = "useItemOn")
    private ItemInteractionResult vanillaDisable$useItemOn(ItemStack stack, Level level, Player player, InteractionHand hand, BlockHitResult hitResult, Operation<ItemInteractionResult> original) {
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        if (SqlManager.getBoolean("blocks", block, "can_interact")) {
            return original.call(stack, level, player, hand, hitResult);
        }
        return ItemInteractionResult.FAIL;
    }

    @ModifyReturnValue(method = "getMenuProvider", at = @At("RETURN"))
    private MenuProvider vanillaDisable$modifyMenuProvider(MenuProvider original) {
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_interact")) {
            return null;
        }
        return original;
    }
}
