/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning.spawning;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(Villager.class)
public abstract class MixinVillager {
    @ModifyReturnValue(method = "wantsToSpawnGolem", at = @At("RETURN"))
    private boolean vanillaDisable$wantsToSpawnGolem(boolean original) {
        return original && SqlManager.getBoolean("entities", "minecraft:iron_golem", "spawned_by_villagers");
    }
}
