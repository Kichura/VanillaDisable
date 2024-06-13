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
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(BiomeGenerationSettings.class)
public abstract class MixinBiomeGenerationSettings {
    @ModifyReturnValue(method = "hasFeature", at = @At("RETURN"))
    private boolean vanillaDisable$hasFeature(boolean original, PlacedFeature feature) {
        if (DataDefinitions.placedFeatureRegistry == null) return original;
        String rule = Objects.requireNonNull(DataDefinitions.placedFeatureRegistry.getKey(feature)).toString();
        if (!SqlManager.worldgenMaps.get("placed_features").isEmpty() && !SqlManager.worldgenMaps.get("placed_features").getOrDefault(rule, true)) {
            return false;
        }
        if (DataDefinitions.populationDone && !SqlManager.getBoolean("placed_features", rule, "enabled")) {
            return false;
        }
        return original;
    }
}
