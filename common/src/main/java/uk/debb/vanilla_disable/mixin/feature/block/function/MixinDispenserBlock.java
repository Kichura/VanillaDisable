/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(DispenserBlock.class)
public abstract class MixinDispenserBlock {
    @Inject(method = "dispenseFrom", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$dispenseFrom(CallbackInfo ci) {
        if (!DataHandler.getCachedBoolean("blocks", "minecraft:dispenser", "works")) {
            ci.cancel();
        }
    }
}
