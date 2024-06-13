/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(Enchantment.class)
public abstract class MixinEnchantment {
    @ModifyReturnValue(method = "canEnchant", at = @At("RETURN"))
    private boolean vanillaDisable$canEnchant(boolean original, ItemStack stack) {
        if (CommandDataHandler.isConnectionNull()) return original;
        if (stack.getMaxDamage() == 0) return original;
        String item = "can_enchant_" + CommandDataHandler.lightCleanup(CommandDataHandler.getKeyFromItemRegistry(stack.getItem()));
        ResourceLocation enchantment = CommandDataHandler.enchantmentRegistry.getKey((Enchantment) (Object) this);
        if (enchantment == null) return original;
        return CommandDataHandler.getCachedBoolean("enchantments", enchantment.toString(), item);
    }

    @ModifyReturnValue(method = "areCompatible", at = @At("RETURN"))
    private static boolean vanillaDisable$areCompatible(boolean original, Holder<Enchantment> holder, Holder<Enchantment> holder2) {
        if (CommandDataHandler.isConnectionNull()) return original;
        ResourceLocation enchantmentRL = CommandDataHandler.enchantmentRegistry.getKey(holder.value());
        ResourceLocation otherEnchantmentRL = CommandDataHandler.enchantmentRegistry.getKey(holder2.value());
        if (enchantmentRL == null || otherEnchantmentRL == null) return original;
        String enchantment = enchantmentRL.toString();
        String otherEnchantment = "compatible_with_" + CommandDataHandler.lightCleanup(otherEnchantmentRL);
        String reversedEnchantment = enchantment.replace("minecraft:", "compatible_with_");
        String reversedOtherEnchantment = otherEnchantment.replace("compatible_with_", "minecraft:");
        return CommandDataHandler.getCachedBoolean("enchantments", enchantment, otherEnchantment) ||
                CommandDataHandler.getCachedBoolean("enchantments", reversedOtherEnchantment, reversedEnchantment);
    }
}
