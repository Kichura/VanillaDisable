/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {
    @Shadow
    private int age;
    @Shadow
    private int pickupDelay;

    @SuppressWarnings("resource")
    @Inject(method = "tick", at = @At("HEAD"))
    private void vanillaDisable$tick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (this.age >= SqlManager.getInt("entities", "minecraft:item", "despawn_time") && !entity.level().isClientSide()) {
            entity.discard();
        }
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V",
                    ordinal = 1
            )
    )
    private boolean vanillaDisable$discard(ItemEntity instance) {
        return this.pickupDelay == Short.MAX_VALUE || this.age >= SqlManager.getInt("entities", "minecraft:item", "despawn_time");
    }
}
