/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning.spawning;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(CatSpawner.class)
public abstract class MixinCatSpawner {
    @ModifyExpressionValue(
            method = "spawnCat",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"
            )
    )
    private Entity vanillaDisable$create(Entity original) {
        return SqlManager.getBoolean("entities", "minecraft:cat", "spawned_by_villagers") ? original : null;
    }
}
