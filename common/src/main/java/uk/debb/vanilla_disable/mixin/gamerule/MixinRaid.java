/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.gamerule;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.gamerule.VDGamerules;

@Mixin(Raid.class)
public abstract class MixinRaid {
    @ModifyReturnValue(method = "getNumGroups", at = @At("RETURN"))
    private int vanillaDisable$getNumGroups(int original, Difficulty difficulty) {
        if (VDGamerules.server == null) return original;
        return switch (difficulty) {
            case PEACEFUL -> 0;
            case EASY -> VDGamerules.server.getGameRules().getInt(VDGamerules.RAID_WAVES_EASY) - 1;
            case NORMAL -> VDGamerules.server.getGameRules().getInt(VDGamerules.RAID_WAVES_NORMAL) - 1;
            case HARD -> VDGamerules.server.getGameRules().getInt(VDGamerules.RAID_WAVES_HARD) - 1;
        };
    }
}
