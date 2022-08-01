package uk.debb.vanilla_disable.util.maps;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.StructureType;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

public interface Maps {
    Object2ObjectMap<MobEffect, Gamerules> livingEntityMobEffectMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> biomeGenerationSettingsStringMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Item, Gamerules> dispenserBlockItemMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Potion, Gamerules> arrowPotionMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> goalClassMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<StructureType<?>, Gamerules> structureCheckStructureTypeMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> structureCheckStringMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Potion, Gamerules> potionUtilsPotionMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> itemStackClassMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> serverPlayerStringMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> commandsStringMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> commandsStringMapDedicated = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> baseSpawnerClassMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<MobCategory, Gamerules> naturalSpawnerMobCategoryMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<DamageSource, Gamerules> playerDamageSourceMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<FoodProperties, Gamerules> foodDataFoodPropertiesMapNutrition = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<FoodProperties, Gamerules> foodDataFoodPropertiesMapSaturation = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> livingEntityClassMapKnockback = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> mobClassMapDespawn = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<MobCategory, Gamerules> mobCategoryMobCategoryMapMax = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<MobCategory, Gamerules> mobCategoryMobCategoryMapMin = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<MobCategory, Gamerules> mobCategoryMobCategoryMapMobcap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Enchantment, Gamerules> enchantmentHelperEnchantmentMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Class<?>, Gamerules> mobClassMapToggle = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<String, Gamerules> playerAdvancementsStringMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Item, Gamerules> abstractCauldronBlockItemMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Block, Gamerules> fallingBlockBlockMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Holder<PaintingVariant>, Gamerules> paintingHolderPaintingVariantMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<VillagerType, Gamerules> villagerDataVillagerTypeMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<VillagerProfession, Gamerules> villagerDataVillagerProfessionMap = new Object2ObjectOpenHashMap<>();
    Object2ObjectMap<Block, Gamerules> blockStateBaseBlockMap = new Object2ObjectOpenHashMap<>();
}
