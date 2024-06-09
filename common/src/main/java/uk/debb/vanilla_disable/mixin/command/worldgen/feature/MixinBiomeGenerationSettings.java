/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.worldgen.feature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

import java.util.Objects;

@Mixin(BiomeGenerationSettings.class)
public abstract class MixinBiomeGenerationSettings {
    @ModifyReturnValue(method = "hasFeature", at = @At("RETURN"))
    private boolean vanillaDisable$hasFeature(boolean original, PlacedFeature feature) {
        if (CommandDataHandler.placedFeatureRegistry == null || CommandDataHandler.server == null) return original;
        String rule = Objects.requireNonNull(CommandDataHandler.placedFeatureRegistry.getKey(feature)).toString();
        if (!CommandDataHandler.placedFeatureMap.isEmpty() && !CommandDataHandler.placedFeatureMap.getOrDefault(rule, true)) {
            return false;
        }
        if (CommandDataHandler.populationDone && !CommandDataHandler.getCachedBoolean("placed_features", rule, "enabled")) {
            return false;
        }
        return original;
    }
}
