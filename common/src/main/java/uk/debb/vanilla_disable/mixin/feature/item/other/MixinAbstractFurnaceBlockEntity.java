/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {
    @ModifyReturnValue(method = "isFuel", at = @At("RETURN"))
    private static boolean vanillaDisable$isFuel(boolean original, ItemStack stack) {
        String item = DataUtils.getKeyFromItemRegistry(stack.getItem());
        if (SqlManager.getInt("items", item, "fuel_duration") <= 0) {
            return false;
        }
        return original;
    }

    @ModifyReturnValue(method = "getBurnDuration", at = @At("RETURN"))
    private int vanillaDisable$getBurnDuration(int original, ItemStack fuel) {
        String item = DataUtils.getKeyFromItemRegistry(fuel.getItem());
        return SqlManager.getInt("items", item, "fuel_duration");
    }
}
