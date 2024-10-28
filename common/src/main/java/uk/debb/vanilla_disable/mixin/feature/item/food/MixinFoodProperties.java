/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(value = FoodProperties.class, priority = 999)
public abstract class MixinFoodProperties {
    @Shadow
    public abstract boolean canAlwaysEat();

    @WrapOperation(
            method = "onConsume",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"
            )
    )
    private void vanillaDisable$onConsume(FoodData instance, FoodProperties foodProperties, Operation<Void> original, Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable) {
        int nutrition = SqlManager.getInt("items", DataUtils.getKeyFromItemRegistry(itemStack.getItem()), "nutrition");
        double saturation = SqlManager.getDouble("items", DataUtils.getKeyFromItemRegistry(itemStack.getItem()), "saturation");
        instance.eat(new FoodProperties(nutrition, (float) saturation, canAlwaysEat()));
    }
}
