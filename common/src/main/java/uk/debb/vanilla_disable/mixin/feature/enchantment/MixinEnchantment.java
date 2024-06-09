/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(Enchantment.class)
public abstract class MixinEnchantment {
    @ModifyReturnValue(method = "canEnchant", at = @At("RETURN"))
    private boolean vanillaDisable$canEnchant(boolean original, ItemStack stack) {
        if (DataHandler.isConnectionNull()) return original;
        if (stack.getMaxDamage() == 0) return original;
        String item = "can_enchant_" + DataHandler.lightCleanup(DataHandler.getKeyFromItemRegistry(stack.getItem()));
        ResourceLocation enchantment = DataHandler.enchantmentRegistry.getKey((Enchantment) (Object) this);
        if (enchantment == null) return original;
        return DataHandler.getCachedBoolean("enchantments", enchantment.toString(), item);
    }

    @ModifyReturnValue(method = "areCompatible", at = @At("RETURN"))
    private static boolean vanillaDisable$areCompatible(boolean original, Holder<Enchantment> holder, Holder<Enchantment> holder2) {
        if (DataHandler.isConnectionNull()) return original;
        ResourceLocation enchantmentRL = DataHandler.enchantmentRegistry.getKey(holder.value());
        ResourceLocation otherEnchantmentRL = DataHandler.enchantmentRegistry.getKey(holder2.value());
        if (enchantmentRL == null || otherEnchantmentRL == null) return original;
        String enchantment = enchantmentRL.toString();
        String otherEnchantment = "compatible_with_" + DataHandler.lightCleanup(otherEnchantmentRL);
        String reversedEnchantment = enchantment.replace("minecraft:", "compatible_with_");
        String reversedOtherEnchantment = otherEnchantment.replace("compatible_with_", "minecraft:");
        return DataHandler.getCachedBoolean("enchantments", enchantment, otherEnchantment) ||
                DataHandler.getCachedBoolean("enchantments", reversedOtherEnchantment, reversedEnchantment);
    }
}
