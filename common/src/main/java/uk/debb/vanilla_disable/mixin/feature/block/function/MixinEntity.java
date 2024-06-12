/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Inject(method = "onAboveBubbleCol", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$onAboveBubbleCol(CallbackInfo ci) {
        if (!SqlManager.getBoolean("blocks", "minecraft:bubble_column", "works")) {
            ci.cancel();
        }
    }

    @Inject(method = "onInsideBubbleColumn", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$onInsideBubbleColumn(CallbackInfo ci) {
        if (!SqlManager.getBoolean("blocks", "minecraft:bubble_column", "works")) {
            ci.cancel();
        }
    }
}
