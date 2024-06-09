/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyReturnValue(method = "canBeAffected", at = @At("RETURN"))
    private boolean vanillaDisable$canBeAffected(boolean original, MobEffectInstance effectInstance) {
        if (DataHandler.isConnectionNull()) return original;
        String entity = DataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        return DataHandler.getCachedBoolean("entities", entity,
                DataHandler.lightCleanup(Objects.requireNonNull(DataHandler.mobEffectRegistry.getKey(effectInstance.getEffect().value()))) + "_effect");
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V"
            )
    )
    private boolean vanillaDisable$aiStep(LivingEntity livingEntity) {
        String entity = DataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        return DataHandler.getCachedBoolean("entities", entity, "ai");
    }
}
