package uk.debb.vanilla_disable.mixin.enchantments.enchantment_conflicts;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MultiShotEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(MultiShotEnchantment.class)
public abstract class MixinMultiShotEnchantment extends Enchantment {
    public MixinMultiShotEnchantment(Enchantment.Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.CROSSBOW, equipmentSlots);
    }

    @ModifyReturnValue(method = "checkCompatibility", at = @At("RETURN"))
    private boolean cancelCompatibility(boolean original, Enchantment enchantment) {
        if (!Gamerules.CROSSBOW_ENCHANTMENT_CONFLICTS.getValue(Boolean::parseBoolean)) {
            return super.checkCompatibility(enchantment);
        }
        return original;
    }
}