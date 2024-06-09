/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.feature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

import java.util.Objects;

@Mixin(BiomeGenerationSettings.class)
public abstract class MixinBiomeGenerationSettings {
    @ModifyReturnValue(method = "hasFeature", at = @At("RETURN"))
    private boolean vanillaDisable$hasFeature(boolean original, PlacedFeature feature) {
        if (DataHandler.placedFeatureRegistry == null || DataHandler.server == null) return original;
        String rule = Objects.requireNonNull(DataHandler.placedFeatureRegistry.getKey(feature)).toString();
        if (!DataHandler.placedFeatureMap.isEmpty() && !DataHandler.placedFeatureMap.getOrDefault(rule, true)) {
            return false;
        }
        if (DataHandler.populationDone && !DataHandler.getCachedBoolean("placed_features", rule, "enabled")) {
            return false;
        }
        return original;
    }
}
