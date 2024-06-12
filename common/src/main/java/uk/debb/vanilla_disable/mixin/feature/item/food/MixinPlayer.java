/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.food;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Shadow
    public abstract FoodData getFoodData();

    @Inject(method = "eat", at = @At("HEAD"))
    private void vanillaDisable$eat(Level level, ItemStack itemStack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        String name = DataUtils.getKeyFromItemRegistry(itemStack.getItem());
        int nutrition = SqlManager.getInt("items", name, "nutrition");
        float saturation = (float) SqlManager.getDouble("items", name, "saturation");
        this.getFoodData().eat(nutrition, saturation);
    }

    @WrapWithCondition(
            method = "eat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"
            )
    )
    private boolean vanillaDisable$eat1(FoodData instance, FoodProperties foodProperties) {
        return false;
    }
}
