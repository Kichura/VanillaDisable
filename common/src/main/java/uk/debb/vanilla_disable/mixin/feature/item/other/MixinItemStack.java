/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow
    public abstract Item getItem();

    @ModifyReturnValue(method = "getMaxDamage", at = @At("RETURN"))
    private int vanillaDisable$getMaxDamage(int original) {
        if (DataHandler.isConnectionNull()) return original;
        if (original == 0) return original;
        String item = DataHandler.getKeyFromItemRegistry(this.getItem());
        return DataHandler.getCachedInt("items", item, "durability");
    }

    @ModifyReturnValue(method = "canBeHurtBy", at = @At("RETURN"))
    private boolean vanillaDisable$canBeHurtBy(boolean original, DamageSource damageSource) {
        String item = DataHandler.getKeyFromItemRegistry(this.getItem());
        if (damageSource.is(DamageTypeTags.IS_FIRE)) {
            return DataHandler.getCachedBoolean("items", item, "burns");
        }
        return original;
    }
}
