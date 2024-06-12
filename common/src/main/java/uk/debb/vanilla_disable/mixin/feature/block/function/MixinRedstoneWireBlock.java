/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(RedStoneWireBlock.class)
public abstract class MixinRedstoneWireBlock {
    @ModifyReturnValue(method = "getWireSignal", at = @At("RETURN"))
    private int vanillaDisable$getWireSignal(int original) {
        if (!SqlManager.getBoolean("blocks", "minecraft:redstone_wire", "works")) {
            return 0;
        }
        return original;
    }
}
