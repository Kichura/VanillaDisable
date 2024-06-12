/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player.hunger;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(CakeBlock.class)
public abstract class MixinCakeBlock {
    @Inject(
            method = "eat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"
            )
    )
    private static void vanillaDisable$eat(LevelAccessor level, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<InteractionResult> cir) {
        if (SqlManager.getBoolean("entities", "minecraft:player", "beta_hunger")) {
            int nutrition = SqlManager.getInt("items", "minecraft:cake", "nutrition");
            player.setHealth(player.getHealth() + nutrition);
        }
    }
}
