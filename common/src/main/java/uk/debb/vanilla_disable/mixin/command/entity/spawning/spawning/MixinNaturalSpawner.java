/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.entity.spawning.spawning;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(NaturalSpawner.class)
public abstract class MixinNaturalSpawner {
    @ModifyReturnValue(method = "isValidPositionForMob", at = @At("RETURN"))
    private static boolean vanillaDisable$isValidPositionForMob(boolean original, ServerLevel serverLevel, Mob mob, double d) {
        String entity = CommandDataHandler.getKeyFromEntityTypeRegistry(mob.getType());
        return original && CommandDataHandler.getCachedBoolean("entities", entity, "can_spawn");
    }
}
