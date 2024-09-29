/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.interaction;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$useWithoutItem(CallbackInfoReturnable<InteractionResult> cir) {
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_interact")) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$useItemOn(CallbackInfoReturnable<ItemInteractionResult> cir) {
        String block = DataUtils.getKeyFromBlockRegistry(this.getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_interact")) {
            cir.setReturnValue(ItemInteractionResult.FAIL);
        }
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
