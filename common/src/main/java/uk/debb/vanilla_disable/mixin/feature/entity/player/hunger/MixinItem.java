/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player.hunger;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Item.class)
public abstract class MixinItem {
    @ModifyReturnValue(method = "getUseDuration", at = @At("RETURN"))
    private int vanillaDisable$getUseDuration(int original, ItemStack stack) {
        if (stack.get(DataComponents.FOOD) != null && SqlManager.getBoolean("entities", "minecraft:player", "beta_hunger")) {
            return 1;
        }
        return original;
    }
}
