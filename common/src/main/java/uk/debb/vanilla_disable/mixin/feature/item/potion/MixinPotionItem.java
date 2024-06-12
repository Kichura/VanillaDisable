/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.potion;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;
import java.util.Optional;

@Mixin(PotionItem.class)
public abstract class MixinPotionItem {
    @ModifyExpressionValue(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object vanillaDisable$getOrDefault(Object original) {
        String item = DataUtils.getKeyFromItemRegistry((PotionItem) (Object) this);
        Optional<Holder<Potion>> potion = ((PotionContents) original).potion();
        if (potion.isEmpty()) return original;
        String pot = DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.potionRegistry.getKey(potion.get().value())));
        if (!SqlManager.getBoolean("items", item, pot + "_effect")) {
            return PotionContents.EMPTY;
        }
        return original;
    }
}
