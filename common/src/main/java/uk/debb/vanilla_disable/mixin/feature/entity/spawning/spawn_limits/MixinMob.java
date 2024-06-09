/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning.spawn_limits;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(Mob.class)
public abstract class MixinMob {
    @ModifyExpressionValue(
            method = "checkDespawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/MobCategory;getNoDespawnDistance()I"
            )
    )
    private int vanillaDisable$getNoDespawnDistance(int original) {
        Mob mob = (Mob) (Object) this;
        String entity = DataHandler.getKeyFromEntityTypeRegistry(mob.getType());
        return DataHandler.getCachedInt("entities", entity, "min_despawn_distance");
    }

    @ModifyExpressionValue(
            method = "checkDespawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/MobCategory;getDespawnDistance()I"
            )
    )
    private int vanillaDisable$getDespawnDistance(int original) {
        Mob mob = (Mob) (Object) this;
        String entity = DataHandler.getKeyFromEntityTypeRegistry(mob.getType());
        return DataHandler.getCachedInt("entities", entity, "instant_despawn_distance");
    }
}
