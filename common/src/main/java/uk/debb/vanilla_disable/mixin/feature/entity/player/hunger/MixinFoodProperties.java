/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player.hunger;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(FoodProperties.class)
public abstract class MixinFoodProperties {
    @Inject(
            method = "onConsume",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"
            )
    )
    private void vanillaDisable$onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable, CallbackInfo ci) {
        int nutrition = SqlManager.getInt("items", DataUtils.getKeyFromItemRegistry(itemStack.getItem()), "nutrition");
        if (SqlManager.getBoolean("entities", "minecraft:player", "beta_hunger")) {
            livingEntity.setHealth(livingEntity.getHealth() + nutrition);
        }
    }
}
