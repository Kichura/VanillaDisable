/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.entity.breeding.tempt_goal;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(Pig.class)
public abstract class MixinPig {
    @ModifyArg(
            method = "registerGoals",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/TemptGoal;<init>(Lnet/minecraft/world/entity/PathfinderMob;DLnet/minecraft/world/item/crafting/Ingredient;Z)V",
                    ordinal = 1
            ),
            index = 2
    )
    private Ingredient vanillaDisable$TemptGoal(Ingredient original) {
        if (CommandDataHandler.isConnectionNull()) return original;
        return CommandDataHandler.getCachedBreedingItems("minecraft:pig");
    }

    @WrapWithCondition(
            method = "registerGoals",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
                    ordinal = 3
            )
    )
    private boolean vanillaDisable$addGoal(GoalSelector goalSelector, int priority, Goal goal) {
        return false;
    }
}
