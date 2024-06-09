/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

import java.util.HashSet;
import java.util.Set;

@Mixin(BiomeSource.class)
public abstract class MixinBiomeSource {
    @ModifyReturnValue(method = "possibleBiomes", at = @At("RETURN"))
    private Set<Holder<Biome>> vanillaDisable$possibleBiomes(Set<Holder<Biome>> original) {
        if (CommandDataHandler.biomeRegistry == null || CommandDataHandler.server == null) return original;
        Set<Holder<Biome>> set = new HashSet<>(original);
        for (Holder<Biome> biomeHolder : original) {
            ResourceLocation resourceLocation = CommandDataHandler.biomeRegistry.getKey(biomeHolder.value());
            if (resourceLocation == null) continue;
            if (!CommandDataHandler.biomeMap.isEmpty() && !CommandDataHandler.biomeMap.getOrDefault(resourceLocation.toString(), true)) {
                set.remove(biomeHolder);
            }
            if (CommandDataHandler.populationDone && !CommandDataHandler.getCachedBoolean("biomes", resourceLocation.toString(), "enabled")) {
                set.remove(biomeHolder);
            }
        }
        return set;
    }
}
