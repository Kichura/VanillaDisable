/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.CauldronBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(CauldronBlock.class)
public abstract class MixinCauldronBlock {
    @ModifyReturnValue(method = "shouldHandlePrecipitation", at = @At("RETURN"))
    private static boolean vanillaDisable$shouldHandlePrecipitation(boolean original) {
        return original && SqlManager.getBoolean("blocks", "minecraft:cauldron", "can_be_filled_by_precipitation");
    }

    @ModifyReturnValue(method = "canReceiveStalactiteDrip", at = @At("RETURN"))
    private boolean vanillaDisable$canReceiveStalactiteDrip(boolean original) {
        return SqlManager.getBoolean("blocks", "minecraft:cauldron", "can_be_filled_by_dripstone");
    }
}
