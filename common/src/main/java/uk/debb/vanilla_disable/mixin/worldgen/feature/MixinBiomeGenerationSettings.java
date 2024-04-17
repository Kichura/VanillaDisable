/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.worldgen.feature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.worldgen.WorldgenDataHandler;

import java.util.Objects;

@Mixin(BiomeGenerationSettings.class)
public abstract class MixinBiomeGenerationSettings {
    @ModifyReturnValue(method = "hasFeature", at = @At("RETURN"))
    private boolean vanillaDisable$hasFeature(boolean original, PlacedFeature feature) {
        String rule = WorldgenDataHandler.cleanup(Objects.requireNonNull(WorldgenDataHandler.placedFeatureRegistry.getKey(feature)));
        if (!WorldgenDataHandler.get("placed_features", rule)) {
            return false;
        }
        return original;
    }
}
