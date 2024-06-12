/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.data;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

import static uk.debb.vanilla_disable.config.data.DataDefinitions.*;

public class DataUtils {
    public static String cleanup(Object o) {
        String s = o.toString().replace("_", " ");
        if (s.contains(":")) {
            s = s.split(":")[1];
        }
        if (s.contains("/")) {
            s = s.split("/")[1];
        }
        return s;
    }

    public static String lightCleanup(Object o) {
        String s = o.toString();
        if (s.contains(":")) {
            s = s.split(":")[1];
        }
        return s;
    }

    public static String getKeyFromBlockRegistry(Block block) {
        return Objects.requireNonNull(blockRegistry.getKey(block)).toString();
    }

    public static String getKeyFromItemRegistry(Item item) {
        return Objects.requireNonNull(itemRegistry.getKey(item)).toString();
    }

    public static String getKeyFromEntityTypeRegistry(EntityType<?> entityType) {
        return Objects.requireNonNull(entityTypeRegistry.getKey(entityType)).toString();
    }

    public static String getKeyFromEnchantmentRegistry(Enchantment enchantment) {
        return Objects.requireNonNull(enchantmentRegistry.getKey(enchantment)).toString();
    }

    private static boolean hasBiome(ResourceKey<Level> level, Holder<Biome> biome) {
        return Objects.requireNonNull(server.getLevel(level)).getChunkSource().getGenerator().getBiomeSource().possibleBiomes.get().contains(biome);
    }

    public static Holder<Biome> getDefaultBiome(Holder<Biome> biome) {
        if (hasBiome(Level.NETHER, biome)) {
            return biomeRegistry.getHolderOrThrow(Biomes.NETHER_WASTES);
        } else if (hasBiome(Level.END, biome)) {
            return biomeRegistry.getHolderOrThrow(Biomes.THE_END);
        }
        return biomeRegistry.getHolderOrThrow(Biomes.PLAINS);
    }

    public static Holder<Biome> getBiome(Holder<Biome> original) {
        if (biomeRegistry == null || server == null) return original;
        ResourceLocation resourceLocation = biomeRegistry.getKey(original.value());
        if (resourceLocation == null) return original;
        if (resourceLocation.equals(Biomes.PLAINS.location())) return original;
        if (resourceLocation.equals(Biomes.NETHER_WASTES.location())) return original;
        if (resourceLocation.equals(Biomes.THE_END.location())) return original;
        if (!SqlManager.biomeMap.isEmpty() && !SqlManager.biomeMap.getOrDefault(resourceLocation.toString(), true)) {
            return getDefaultBiome(original);
        }
        if (populationDone && !SqlManager.getBoolean("biomes", resourceLocation.toString(), "enabled")) {
            return getDefaultBiome(original);
        }
        return original;
    }
}
