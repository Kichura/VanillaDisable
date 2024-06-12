/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Abilities.class)
public abstract class MixinAbilities {
    @Shadow
    private float flyingSpeed;

    @ModifyReturnValue(method = "getFlyingSpeed", at = @At("RETURN"))
    private float vanillaDisable$getFlyingSpeed(float original) {
        return (float) SqlManager.getDouble("entities", "minecraft:player", "flying_speed") / 0.05F * this.flyingSpeed;
    }
}
