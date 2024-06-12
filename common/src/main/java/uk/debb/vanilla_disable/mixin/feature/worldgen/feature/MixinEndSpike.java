/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.feature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(SpikeFeature.EndSpike.class)
public abstract class MixinEndSpike {
    @ModifyReturnValue(method = "isGuarded", at = @At("RETURN"))
    private boolean vanillaDisable$isGuarded(boolean original) {
        return original && SqlManager.getBoolean("placed_features", "minecraft_unofficial:end_spike_cage", "enabled");
    }
}
