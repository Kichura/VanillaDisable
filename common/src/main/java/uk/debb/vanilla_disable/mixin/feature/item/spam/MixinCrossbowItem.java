/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.spam;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(CrossbowItem.class)
public abstract class MixinCrossbowItem {
    @ModifyReturnValue(method = "getChargeDuration", at = @At("RETURN"))
    private static int vanillaDisable$getChargeDuration(int original) {
        return SqlManager.getBoolean("items", "minecraft:crossbow", "can_spam") ? 1 : original;
    }

    @ModifyReturnValue(method = "getUseDuration", at = @At("RETURN"))
    private int vanillaDisable$getUseDuration(int original) {
        return SqlManager.getBoolean("items", "minecraft:crossbow", "can_spam") ? 2 : original;
    }
}
