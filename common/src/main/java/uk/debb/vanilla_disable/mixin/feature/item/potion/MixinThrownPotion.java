/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.potion;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

import java.util.Objects;
import java.util.Optional;

@Mixin(ThrownPotion.class)
public abstract class MixinThrownPotion {
    @Unique
    private Object vanillaDisable$calculateEffect(Object original) {
        String item = DataHandler.getKeyFromItemRegistry(((ThrownPotion) (Object) this).getItem().getItem());
        Optional<Holder<Potion>> potion = ((PotionContents) original).potion();
        if (potion.isEmpty()) return original;
        String pot = DataHandler.lightCleanup(Objects.requireNonNull(DataHandler.potionRegistry.getKey(potion.get().value())));
        if (!DataHandler.getCachedBoolean("items", item, pot + "_effect")) {
            return PotionContents.EMPTY;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "onHitBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object vanillaDisable$getOrDefault1(Object original) {
        return vanillaDisable$calculateEffect(original);
    }

    @ModifyExpressionValue(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object vanillaDisable$getOrDefault2(Object original) {
        return vanillaDisable$calculateEffect(original);
    }
}
