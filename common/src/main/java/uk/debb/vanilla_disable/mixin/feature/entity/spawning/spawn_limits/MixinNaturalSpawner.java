/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning.spawn_limits;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(NaturalSpawner.class)
public abstract class MixinNaturalSpawner {
    @ModifyExpressionValue(
            method = "isValidPositionForMob",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/MobCategory;getDespawnDistance()I"
            )
    )
    private static int vanillaDisable$getDespawnDistance(int original, ServerLevel serverLevel, Mob mob, double d) {
        String entity = DataUtils.getKeyFromEntityTypeRegistry(mob.getType());
        return SqlManager.getInt("entities", entity, "instant_despawn_distance");
    }
}
