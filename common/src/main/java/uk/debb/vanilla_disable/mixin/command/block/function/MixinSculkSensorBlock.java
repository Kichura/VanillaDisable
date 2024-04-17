/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.block.function;

import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(SculkSensorBlock.class)
public abstract class MixinSculkSensorBlock {
    @Inject(method = "canActivate", at = @At("HEAD"), cancellable = true)
    private static void vanillaDisable$canActivate(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        String type = CommandDataHandler.getKeyFromBlockRegistry(state.getBlock());
        if (!CommandDataHandler.getCachedBoolean("blocks", type, "works")) {
            cir.setReturnValue(false);
        }
    }
}
