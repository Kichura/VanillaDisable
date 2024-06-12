/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(EnchantmentHelper.class)
public abstract class MixinEnchantmentHelper {
    @ModifyReturnValue(method = "getItemEnchantmentLevel", at = @At("RETURN"))
    private static int vanillaDisable$getItemEnchantmentLevel(int original, Holder<Enchantment> holder, ItemStack itemStack) {
        String item = "can_enchant_" + DataUtils.lightCleanup(DataUtils.getKeyFromItemRegistry(itemStack.getItem()));
        if (!SqlManager.getBoolean("enchantments", DataUtils.getKeyFromEnchantmentRegistry(holder.value()), item)) {
            DataUtils.editAndGetEnchantments(item, itemStack);
            return 0;
        }
        return original;
    }
}
