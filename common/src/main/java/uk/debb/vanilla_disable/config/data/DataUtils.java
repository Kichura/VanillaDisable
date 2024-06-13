/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.data;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;

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
        if (!SqlManager.worldgenMaps.get("biomes").isEmpty() && !SqlManager.worldgenMaps.get("biomes").getOrDefault(resourceLocation.toString(), true)) {
            return getDefaultBiome(original);
        }
        if (populationDone && !SqlManager.getBoolean("biomes", resourceLocation.toString(), "enabled")) {
            return getDefaultBiome(original);
        }
        return original;
    }

    public static ItemStack editAndGetEnchantments(String item, ItemStack itemStack) {
        ItemEnchantments itemEnchantments = itemStack.getEnchantments();
        itemEnchantments.enchantments = itemEnchantments.enchantments.object2IntEntrySet().stream()
                .filter(e -> SqlManager.getBoolean("enchantments", DataUtils.getKeyFromEnchantmentRegistry(e.getKey().value()), item))
                .collect(Object2IntOpenHashMap::new, (m, e) -> m.put(e.getKey(), e.getIntValue()), Object2IntOpenHashMap::putAll);
        EnchantmentHelper.setEnchantments(itemStack, itemEnchantments);
        return itemStack;
    }

    public static PotionContents getPotionContents(PotionContents original, String item) {
        Optional<Holder<Potion>> potion = original.potion();
        if (potion.isEmpty()) return original;
        String pot = DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.potionRegistry.getKey(potion.get().value())));
        if (!SqlManager.getBoolean("items", item, pot + "_effect")) {
            return PotionContents.EMPTY;
        }
        return original;
    }
}
