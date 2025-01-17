/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow
    public abstract EntityType<?> getType();

    @ModifyReturnValue(method = "isOnFire", at = @At("RETURN"))
    private boolean vanillaDisable$isOnFire(boolean original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        return original && SqlManager.getBoolean("entities", "minecraft:player", "can_be_on_fire");
    }

    @ModifyReturnValue(method = "isShiftKeyDown", at = @At("RETURN"))
    private boolean vanillaDisable$isShiftKeyDown(boolean original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        return original && SqlManager.getBoolean("entities", "minecraft:player", "can_crouch");
    }

    @ModifyReturnValue(method = "isSprinting", at = @At("RETURN"))
    private boolean vanillaDisable$isSprinting(boolean original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        return original || !SqlManager.getBoolean("entities", "minecraft:player", "can_sprint");
    }

    @ModifyReturnValue(method = "isSwimming", at = @At("RETURN"))
    private boolean vanillaDisable$isSwimming(boolean original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        return original && SqlManager.getBoolean("entities", "minecraft:player", "can_swim");
    }

    @ModifyReturnValue(method = "isInvisible", at = @At("RETURN"))
    private boolean vanillaDisable$isInvisible(boolean original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        return original && SqlManager.getBoolean("entities", "minecraft:player", "can_be_invisible");
    }

    @ModifyReturnValue(method = "getBlockJumpFactor", at = @At("RETURN"))
    private float vanillaDisable$getBlockJumpFactor(float original) {
        if (!this.getType().equals(EntityType.PLAYER)) return original;
        if (!SqlManager.getBoolean("entities", "minecraft:player", "can_jump")) {
            return 0.0F;
        }
        return original;
    }
}
