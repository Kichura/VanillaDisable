/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ThrownEnderpearl.class)
public abstract class MixinThrownEnderpearl {
    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/ThrownEnderpearl;discard()V"
            )
    )
    private boolean vanillaDisable$discard(ThrownEnderpearl instance) {
        return SqlManager.getBoolean("entities", "minecraft:ender_pearl", "despawn_on_player_death");
    }
}
