/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.spawning;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.function.Function;

@Mixin(BaseSpawner.class)
public abstract class MixinBaseSpawner {
    @Shadow
    protected abstract SpawnData getOrCreateNextSpawnData(@Nullable Level arg, RandomSource arg2, BlockPos arg3);

    @ModifyExpressionValue(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/BaseSpawner;isNearPlayer(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"
            )
    )
    private boolean vanillaDisable$isNearPlayer(boolean original, ServerLevel serverLevel, BlockPos pos) {
        CompoundTag compoundTag = this.getOrCreateNextSpawnData(serverLevel, serverLevel.getRandom(), pos).getEntityToSpawn();
        Entity entity = EntityType.loadEntityRecursive(compoundTag, serverLevel, Function.identity());
        if (entity != null) {
            String entityType = DataUtils.getKeyFromEntityTypeRegistry(entity.getType());
            if (!SqlManager.getBoolean("entities", entityType, "spawner")) {
                return false;
            }
        }
        return original;
    }
}
