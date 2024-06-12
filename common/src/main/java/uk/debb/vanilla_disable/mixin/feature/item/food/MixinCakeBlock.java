/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.food;

import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(CakeBlock.class)
public abstract class MixinCakeBlock {
    @ModifyArgs(
            method = "eat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"
            )
    )
    private static void vanillaDisable$eat(Args args) {
        int nutrition = SqlManager.getInt("items", "minecraft:cake", "nutrition");
        float saturation = (float) SqlManager.getDouble("items", "minecraft:cake", "saturation");
        args.set(0, nutrition);
        args.set(1, saturation);
    }
}
