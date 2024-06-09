/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(EnchantmentHelper.class)
public abstract class MixinEnchantmentHelper {
    @ModifyReturnValue(method = "getItemEnchantmentLevel", at = @At("RETURN"))
    private static int vanillaDisable$getItemEnchantmentLevel(int original, Holder<Enchantment> holder, ItemStack itemStack) {
        String item = "can_enchant_" + DataHandler.lightCleanup(DataHandler.getKeyFromItemRegistry(itemStack.getItem()));
        if (!DataHandler.getCachedBoolean("enchantments", DataHandler.getKeyFromEnchantmentRegistry(holder.value()), item)) {
            ItemEnchantments itemEnchantments = itemStack.getEnchantments();
            itemEnchantments.enchantments = itemEnchantments.enchantments.object2IntEntrySet().stream()
                    .filter(e -> DataHandler.getCachedBoolean("enchantments", DataHandler.getKeyFromEnchantmentRegistry(e.getKey().value()), item))
                    .collect(Object2IntOpenHashMap::new, (m, e) -> m.put(e.getKey(), e.getIntValue()), Object2IntOpenHashMap::putAll);
            EnchantmentHelper.setEnchantments(itemStack, itemEnchantments);
            return 0;
        }
        return original;
    }
}
