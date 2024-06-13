/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.HashSet;
import java.util.Set;

@Mixin(BiomeSource.class)
public abstract class MixinBiomeSource {
    @ModifyReturnValue(method = "possibleBiomes", at = @At("RETURN"))
    private Set<Holder<Biome>> vanillaDisable$possibleBiomes(Set<Holder<Biome>> original) {
        if (DataDefinitions.biomeRegistry == null) return original;
        Set<Holder<Biome>> set = new HashSet<>(original);
        for (Holder<Biome> biomeHolder : original) {
            ResourceLocation resourceLocation = DataDefinitions.biomeRegistry.getKey(biomeHolder.value());
            if (resourceLocation == null) continue;
            if (resourceLocation.equals(Biomes.PLAINS.location())) continue;
            if (resourceLocation.equals(Biomes.NETHER_WASTES.location())) continue;
            if (resourceLocation.equals(Biomes.THE_END.location())) continue;
            if (!SqlManager.worldgenMaps.get("biomes").isEmpty() && !SqlManager.worldgenMaps.get("biomes").getOrDefault(resourceLocation.toString(), true)) {
                set.remove(biomeHolder);
            }
            if (DataDefinitions.populationDone && !SqlManager.getBoolean("biomes", resourceLocation.toString(), "enabled")) {
                set.remove(biomeHolder);
            }
        }
        return set;
    }
}
