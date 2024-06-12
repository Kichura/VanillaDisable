/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.potion;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;
import java.util.Optional;

@Mixin(Arrow.class)
public abstract class MixinArrow {
    @ModifyReturnValue(method = "getPotionContents", at = @At("RETURN"))
    private PotionContents vanillaDisable$getPotionContents(PotionContents original) {
        if (((Arrow) (Object) this).getPickupItemStackOrigin().is(Items.TIPPED_ARROW)) {
            Optional<Holder<Potion>> optionalPotionHolder = original.potion();
            if (optionalPotionHolder.isPresent()) {
                String potion = Objects.requireNonNull(DataDefinitions.potionRegistry.getKey(optionalPotionHolder.get().value())) + "_effect";
                if (!SqlManager.getBoolean("items", "minecraft:tipped_arrow", DataUtils.lightCleanup(potion))) {
                    return PotionContents.EMPTY;
                }
            }
        }
        return original;
    }
}
