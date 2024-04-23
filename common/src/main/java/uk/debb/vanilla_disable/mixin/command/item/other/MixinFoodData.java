/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.item.other;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(FoodData.class)
public abstract class MixinFoodData {
    @Shadow
    public abstract void eat(int i, float f);

    @Inject(method = "eat(Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$eat(ItemStack stack, CallbackInfo ci) {
        if (stack.get(DataComponents.FOOD) != null) {
            String name = CommandDataHandler.getKeyFromItemRegistry(stack.getItem());
            int nutrition = CommandDataHandler.getCachedInt("items", name, "nutrition");
            float saturation = (float) CommandDataHandler.getCachedDouble("items", name, "saturation");
            this.eat(nutrition, saturation);
        }
        ci.cancel();
    }
}
