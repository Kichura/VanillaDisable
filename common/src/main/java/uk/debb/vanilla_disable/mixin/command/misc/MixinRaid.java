/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.misc;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(Raid.class)
public abstract class MixinRaid {
    @ModifyReturnValue(method = "getNumGroups", at = @At("RETURN"))
    private int vanillaDisable$getNumGroups(int original, Difficulty difficulty) {
        if (CommandDataHandler.server == null) return original;
        return switch (difficulty) {
            case PEACEFUL -> 0;
            case EASY -> CommandDataHandler.getCachedInt("misc", "raid_waves_easy", "raid_waves") - 1;
            case NORMAL -> CommandDataHandler.getCachedInt("misc", "raid_waves_normal", "raid_waves") - 1;
            case HARD -> CommandDataHandler.getCachedInt("misc", "raid_waves_hard", "raid_waves") - 1;
        };
    }
}
