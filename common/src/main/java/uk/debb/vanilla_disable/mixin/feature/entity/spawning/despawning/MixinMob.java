/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning.despawning;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Mob.class)
public abstract class MixinMob {
    @Unique
    private boolean vanillaDisable$checkDespawn$additionalRestrictionsMet() {
        if (((Entity) (Object) this).hasCustomName()) return false;
        if (this instanceof Bucketable bucketable) return !bucketable.fromBucket();
        return true;
    }

    @ModifyExpressionValue(
            method = "checkDespawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;removeWhenFarAway(D)Z"
            )
    )
    private boolean vanillaDisable$removeWhenFarAway(boolean original) {
        String entity = DataUtils.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        return SqlManager.getBoolean("entities", entity, "can_despawn") && vanillaDisable$checkDespawn$additionalRestrictionsMet();
    }
}
