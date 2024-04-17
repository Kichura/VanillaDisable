/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.entity.breeding.tempt_goal;

import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(Ocelot.OcelotTemptGoal.class)
public abstract class MixinOcelotTemptGoal {
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/TemptGoal;<init>(Lnet/minecraft/world/entity/PathfinderMob;DLnet/minecraft/world/item/crafting/Ingredient;Z)V"
            ),
            index = 2
    )
    private static Ingredient vanillaDisable$TemptGoal(Ingredient ingredient) {
        if (CommandDataHandler.isConnectionNull()) return ingredient;
        return CommandDataHandler.getCachedBreedingItems("minecraft:ocelot");
    }
}
