/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {
    @Inject(method = "awardStat", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$awardStat(Stat<?> stat, int increment, CallbackInfo ci) {
        if (stat.getType().equals(Stats.CUSTOM)) {
            if (!SqlManager.getBoolean("entities", "minecraft:player",
                    DataUtils.lightCleanup(stat.getName().split(":")[1].replace(".", ":")) + "_custom_stat")) {
                ci.cancel();
            }
        } else {
            if (!SqlManager.getBoolean("entities", "minecraft:player",
                    DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.statTypeRegistry.getKey(stat.getType()))) + "_stat_type")) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$die(DamageSource damageSource, CallbackInfo ci) {
        if (!SqlManager.getBoolean("entities", "minecraft:player",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.damageTypeRegistry.getKey(damageSource.type()))) + "_death")) {
            ((Player) (Object) this).setHealth(1);
            ci.cancel();
        }
    }
}