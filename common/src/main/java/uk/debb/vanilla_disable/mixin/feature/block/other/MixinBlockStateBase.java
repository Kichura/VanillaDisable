/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$useWithoutItem(CallbackInfoReturnable<InteractionResult> cir) {
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        if (!DataHandler.getCachedBoolean("blocks", block, "can_interact")) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$useItemOn(CallbackInfoReturnable<InteractionResult> cir) {
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        if (!DataHandler.getCachedBoolean("blocks", block, "can_interact")) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    @ModifyReturnValue(method = "getMenuProvider", at = @At("RETURN"))
    private MenuProvider vanillaDisable$modifyMenuProvider(MenuProvider original) {
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        if (!DataHandler.getCachedBoolean("blocks", block, "can_interact")) {
            return null;
        }
        return original;
    }

    @ModifyReturnValue(method = "ignitedByLava", at = @At("RETURN"))
    private boolean vanillaDisable$ignitedByLava(boolean original) {
        if (DataHandler.isConnectionNull()) return original;
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        return DataHandler.getCachedBoolean("blocks", block, "ignited_by_lava");
    }

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float vanillaDisable$getDestroySpeed(float original) {
        if (DataHandler.isConnectionNull()) return original;
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        return (float) DataHandler.getCachedDouble("blocks", block, "destroy_speed");
    }

    @ModifyReturnValue(method = "requiresCorrectToolForDrops", at = @At("RETURN"))
    private boolean vanillaDisable$requiresCorrectToolForDrops(boolean original) {
        if (DataHandler.isConnectionNull()) return original;
        String block = DataHandler.getKeyFromBlockRegistry(this.getBlock());
        return DataHandler.getCachedBoolean("blocks", block, "requires_correct_tool_for_drops");
    }
}
