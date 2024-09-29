/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {
    @WrapMethod(method = "awardStat")
    private void vanillaDisable$awardStat(Stat<?> stat, int increment, Operation<Void> original) {
        if (stat.getType().equals(Stats.CUSTOM) && !SqlManager.getBoolean("entities", "minecraft:player",
                    DataUtils.lightCleanup(stat.getName().split(":")[1].replace(".", ":")) + "_custom_stat")) {
            return;
        } else if (!SqlManager.getBoolean("entities", "minecraft:player",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.statTypeRegistry.getKey(stat.getType()))) + "_stat_type")) {
            return;
        }
        original.call(stat, increment);
    }

    @WrapMethod(method = "die")
    private void vanillaDisable$die(DamageSource damageSource, Operation<Void> original) {
        if (SqlManager.getBoolean("entities", "minecraft:player",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.damageTypeRegistry.getKey(damageSource.type()))) + "_death")) {
            original.call(damageSource);
        } else {
            ((Player) (Object) this).setHealth(1);
        }
    }
}
