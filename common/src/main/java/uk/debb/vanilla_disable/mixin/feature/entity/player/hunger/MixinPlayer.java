/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player.hunger;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Inject(method = "eat", at = @At("HEAD"))
    private void vanillaDisable$eat(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (foodProperties != null && SqlManager.getBoolean("entities", "minecraft:player", "beta_hunger")) {
            int nutrition = SqlManager.getInt("items", DataUtils.getKeyFromItemRegistry(food.getItem()), "nutrition");
            livingEntity.setHealth(livingEntity.getHealth() + nutrition);
        }
    }

    @ModifyExpressionValue(
            method = "canEat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;needsFood()Z"
            )
    )
    private boolean vanillaDisable$needsFood(boolean original) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        return SqlManager.getBoolean("entities", "minecraft:player", "beta_hunger") ?
                livingEntity.getHealth() < livingEntity.getMaxHealth() : original;
    }
}
