/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.trading;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(PiglinAi.class)
public abstract class MixinPiglinAi {
    @ModifyReturnValue(method = "isBarterCurrency", at = @At("RETURN"))
    private static boolean vanillaDisable$isBarterCurrency(boolean original) {
        if (!SqlManager.getBoolean("entities", "minecraft:piglin", "can_trade")) {
            return false;
        }
        return original;
    }
}
