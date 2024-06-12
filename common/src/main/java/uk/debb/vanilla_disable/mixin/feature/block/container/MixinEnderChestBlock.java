/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.EnderChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(EnderChestBlock.class)
public abstract class MixinEnderChestBlock {
    @ModifyExpressionValue(
            method = "useWithoutItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
            ),
            require = 0
    )
    private boolean vanillaDisable$isRedstoneConductor(boolean original) {
        return original && SqlManager.getBoolean("blocks", "minecraft:ender_chest", "opening_blockable");
    }
}
