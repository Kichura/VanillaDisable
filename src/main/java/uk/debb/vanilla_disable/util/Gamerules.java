package uk.debb.vanilla_disable.util;

import net.minecraft.world.level.GameRules;

public class Gamerules {
    // Defining the gamerules themselves
    public static GameRules.Key<GameRules.BooleanValue> DAMAGE_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PROJECTILE_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> EXPLOSION_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> FALLING_BLOCK_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> VOID_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> MAGIC_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> CREATIVE_PLAYER_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> LIGHTNING_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> WALL_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> CRAMMING_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> STARVATION_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> CACTUS_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> FLY_INTO_WALL_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> WITHER_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> ANVIL_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> DRAGON_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> SWEET_BERRY_BUSH_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> FALLING_STALACTITE_DAMAGE;

    public static GameRules.Key<GameRules.BooleanValue> KNOCKBACK_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> FIREBALL_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> WITHER_SKULL_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> DRAGON_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> ARROW_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> TRIDENT_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> LLAMA_SPIT_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> SHULKER_BULLET_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> MOB_ATTACK_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_ATTACK_KNOCKBACK;
    public static GameRules.Key<GameRules.BooleanValue> EXPLOSION_KNOCKBACK;

    public static GameRules.Key<GameRules.BooleanValue> MONSTER_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> CREATURE_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> AMBIENT_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> AXOLOTL_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> GLOWSQUID_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> WATER_CREATURE_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> WATER_AMBIENT_SPAWNING;
    public static GameRules.Key<GameRules.BooleanValue> SPAWNERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIG_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> CAVE_SPIDER_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> SILVERFISH_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> ZOMBIE_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> SKELETON_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> BLAZE_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> SPIDER_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> MAGMA_CUBE_SPAWNERS;
    public static GameRules.Key<GameRules.BooleanValue> SPAWN_EGGS;
    public static GameRules.Key<GameRules.BooleanValue> ANIMAL_BREEDING;

    public static GameRules.Key<GameRules.IntegerValue> MIN_SPAWN_DISTANCE;
    public static GameRules.Key<GameRules.BooleanValue> MONSTERS_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> CREATURES_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> AMBIENT_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> AXOLOTLS_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> GLOWSQUIDS_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> WATER_CREATURES_DESPAWN;
    public static GameRules.Key<GameRules.BooleanValue> WATER_AMBIENT_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> MONSTER_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> CREATURE_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> AMBIENT_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> AXOLOTL_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> GLOWSQUID_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> WATER_CREATURE_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> WATER_AMBIENT_MAX_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> MONSTER_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> CREATURE_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> AMBIENT_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> AXOLOTL_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> GLOWSQUID_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> WATER_CREATURE_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> WATER_AMBIENT_MIN_DESPAWN;
    public static GameRules.Key<GameRules.IntegerValue> ITEM_DESPAWN_TIME;
    public static GameRules.Key<GameRules.BooleanValue> ENDER_PEARLS_DESPAWN_ON_DEATH;

    public static GameRules.Key<GameRules.IntegerValue> MONSTER_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> CREATURE_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> AMBIENT_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> AXOLOTL_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> GLOWSQUID_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> WATER_CREATURE_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> WATER_AMBIENT_MOBCAP;
    public static GameRules.Key<GameRules.IntegerValue> MONSTER_MAX_LIGHT_LEVEL;

    public static GameRules.Key<GameRules.BooleanValue> COMMANDS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ADVANCEMENT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> ATTRIBUTE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> BOSS_BAR_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> CHASE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> CLEAR_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> CLONE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> DATAPACK_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> DATA_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> DIFFICULTY_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> EFFECT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> ENCHANT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> EXECUTE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> EXPERIENCE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> FILL_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> FORCE_LOAD_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> FUNCTION_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> GAME_MODE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> GIVE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> HELP_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> ITEM_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> JFR_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> KICK_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> KILL_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> LIST_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> LOCATE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> LOOT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> ME_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> MESSAGE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PARTICLE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PLACE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PLAY_SOUND_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PUBLISH_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> RAID_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> RECIPE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> RELOAD_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> RESET_CHUNKS_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SAY_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SCHEDULE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SCOREBOARD_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SEED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SET_BLOCK_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SET_WORLD_SPAWN_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SPAWN_POINT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SPECTATE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SPREAD_PLAYERS_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> STOP_SOUND_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SUMMON_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TAG_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TEAM_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TEAM_MSG_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TELEPORT_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TELL_RAW_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TIME_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TITLE_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> TRIGGER_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> WEATHER_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> WORLD_BORDER_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> BAN_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> BAN_IP_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> BAN_LIST_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> DE_OP_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> OP_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PARDON_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PARDON_IP_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> PERF_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SAVE_ALL_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SAVE_OFF_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SAVE_ON_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> SET_IDLE_TIMEOUT_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> STOP_DEDICATED_COMMAND;
    public static GameRules.Key<GameRules.BooleanValue> WHITELIST_DEDICATED_COMMAND;

    public static GameRules.Key<GameRules.BooleanValue> INFINITE_WATER;
    public static GameRules.Key<GameRules.BooleanValue> INFINITE_LAVA;
    public static GameRules.Key<GameRules.BooleanValue> WATER_REACHES_FAR;
    public static GameRules.Key<GameRules.BooleanValue> LAVA_REACHES_FAR;
    public static GameRules.Key<GameRules.BooleanValue> LAVA_REACHES_FAR_IN_NETHER;
    public static GameRules.Key<GameRules.IntegerValue> WATER_FLOW_SPEED;
    public static GameRules.Key<GameRules.IntegerValue> LAVA_FLOW_SPEED;
    public static GameRules.Key<GameRules.IntegerValue> LAVA_FLOW_SPEED_NETHER;
    public static GameRules.Key<GameRules.BooleanValue> WATER_PLACEABLE_IN_NETHER;
    public static GameRules.Key<GameRules.BooleanValue> BUBBLE_COLUMNS_ENABLED;

    public static GameRules.Key<GameRules.BooleanValue> CURABLE_ZILLAGERS;
    public static GameRules.Key<GameRules.BooleanValue> VILLAGERS_CONVERT_TO_ZILLAGERS;
    public static GameRules.Key<GameRules.BooleanValue> VILLAGERS_CONVERT_TO_WITCHES;
    public static GameRules.Key<GameRules.BooleanValue> PIGLINS_CONVERT_TO_ZIGLINS;
    public static GameRules.Key<GameRules.BooleanValue> HOGLINS_CONVERT_TO_ZOGLINS;
    public static GameRules.Key<GameRules.BooleanValue> HUSKS_CONVERT_TO_ZOMBIES;
    public static GameRules.Key<GameRules.BooleanValue> ZOMBIES_CONVERT_TO_DROWNED;
    public static GameRules.Key<GameRules.BooleanValue> SKELETONS_CONVERT_TO_STRAYS;
    public static GameRules.Key<GameRules.BooleanValue> INFINITE_TRADING;
    public static GameRules.Key<GameRules.IntegerValue> VILLAGER_DAILY_RESTOCKS;
    public static GameRules.Key<GameRules.BooleanValue> VILLAGER_TRADING_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIGLIN_BARTERING_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIGS_BREED_WITH_WHEAT;
    public static GameRules.Key<GameRules.BooleanValue> MOBS_BURN_IN_SUNLIGHT;
    public static GameRules.Key<GameRules.BooleanValue> DRAGON_FIREBALLS;
    public static GameRules.Key<GameRules.BooleanValue> FIRE_ASPECT_IGNITES_CREEPERS;

    public static GameRules.Key<GameRules.BooleanValue> ALLAYS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> BATS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CATS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CHICKENS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CODS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> COWS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DONKEYS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> FOXES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> FROGS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> HORSES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> MOOSHROOMS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> MULES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> OCELOTS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PARROTS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIGS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PUFFERFISH_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> RABBITS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SALMONS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SHEEP_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SKELETON_HORSES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SNOW_GOLEMS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SQUIDS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> STRIDERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TADPOLES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TROPICAL_FISH_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TURTLES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> VILLAGERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WANDERING_TRADERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> BEES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CAVE_SPIDERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DOLPHINS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ENDERMEN_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> GOATS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> IRON_GOLEMS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> LLAMAS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PANDAS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIGLINS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> POLAR_BEARS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SPIDERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TRADER_LLAMAS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WOLVES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ZOMBIFIED_PIGLINS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> BLAZES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CREEPERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DROWNED_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ELDER_GUARDIANS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ENDERMITES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> EVOKERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> GHASTS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> GUARDIANS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> HOGLINS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> HUSKS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> MAGMA_CUBES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PHANTOMS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PIGLIN_BRUTES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PILLAGERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> RAVAGERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SHULKERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SILVERFISH_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SKELETONS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SLIMES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> STRAYS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> VEXES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> VINDICATORS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WARDENS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WITCHES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WITHER_SKELETONS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ZOGLINS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ZOMBIES_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ZOMBIE_VILLAGERS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DRAGONS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> WITHERS_ENABLED;

    public static GameRules.Key<GameRules.BooleanValue> EFFECTS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ABSORPTION_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> BAD_OMEN_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> BLINDNESS_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> CONDUIT_POWER_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> DARKNESS_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> DOLPHINS_GRACE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> FIRE_RESISTANCE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> GLOWING_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> HASTE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> HEALTH_BOOST_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> HUNGER_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> INSTANT_DAMAGE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> INSTANT_HEALTH_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> INVISIBILITY_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> JUMP_BOOST_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> LEVITATION_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> LUCK_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> MINING_FATIGUE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> NAUSEA_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> NIGHT_VISION_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> POISON_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> REGENERATION_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> RESISTANCE_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> SATURATION_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> SLOWNESS_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> SLOW_FALLING_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> SPEED_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> STRENGTH_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> UNLUCK_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> WATER_BREATHING_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> WEAKNESS_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> WITHER_EFFECT;
    public static GameRules.Key<GameRules.BooleanValue> MILK_CLEARS_EFFECTS;

    public static GameRules.Key<GameRules.BooleanValue> ENCHANTMENTS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> AQUA_AFFINITY_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> BANE_OF_ARTHROPODS_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> BLAST_PROTECTION_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> CHANNELING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> DEPTH_STRIDER_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> EFFICIENCY_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FEATHER_FALLING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FIRE_ASPECT_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FIRE_PROTECTION_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FLAME_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FORTUNE_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> FROST_WALKER_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> IMPALING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> INFINITY_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> KNOCKBACK_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> LOOTING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> LOYALTY_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> LUCK_OF_THE_SEA_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> LURE_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> MENDING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> MULTISHOT_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> PIERCING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> POWER_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> PROJECTILE_PROTECTION_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> PROTECTION_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> PUNCH_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> QUICK_CHARGE_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> RESPIRATION_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> RIPTIDE_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SHARPNESS_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SILK_TOUCH_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SMITE_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SOUL_SPEED_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SWEEPING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> SWIFT_SNEAK_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> THORNS_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> UNBREAKING_ENCHANTMENT;
    public static GameRules.Key<GameRules.BooleanValue> BINDING_CURSE;
    public static GameRules.Key<GameRules.BooleanValue> VANISHING_CURSE;
    public static GameRules.Key<GameRules.BooleanValue> BOOT_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> BOW_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> CROSSBOW_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> DAMAGE_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> MINING_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> PROTECTION_ENCHANTMENT_CONFLICTS;
    public static GameRules.Key<GameRules.BooleanValue> TRIDENT_ENCHANTMENT_CONFLICTS;

    public static GameRules.Key<GameRules.BooleanValue> ANCIENT_CITY_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> BASTION_REMNANT_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> BURIED_TREASURE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> DESERT_PYRAMID_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> END_CITY_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> FORTRESS_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> IGLOO_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> JUNGLE_PYRAMID_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> MANSION_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> MINESHAFT_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> MONUMENT_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> NETHER_FOSSIL_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> OCEAN_RUIN_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> PILLAGER_OUTPOST_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> RUINED_PORTAL_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> SHIPWRECK_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> STRONGHOLD_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> SWAMP_HUT_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> VILLAGE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> END_VEGETATION;
    public static GameRules.Key<GameRules.BooleanValue> NETHER_VEGETATION;
    public static GameRules.Key<GameRules.BooleanValue> OCEAN_VEGETATION;
    public static GameRules.Key<GameRules.BooleanValue> OVERWORLD_VEGETATION;
    public static GameRules.Key<GameRules.BooleanValue> UNDERGROUND_VEGETATION;
    public static GameRules.Key<GameRules.BooleanValue> AMETHYST_GEODE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> BASALT_BLACKSTONE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> DESERT_WELL_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> DRIPSTONE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> DUNGEON_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> END_FEATURES_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> FOSSIL_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> GLOWSTONE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> ICE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> LAVA_LAKE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> MAGMA_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> NETHER_FIRE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> NETHER_ORE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> OCEAN_FLOOR_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> ORE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> SCULK_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> SPRING_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> TREE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> END_PILLAR_CAGE_GENERATION;
    public static GameRules.Key<GameRules.BooleanValue> REMOVE_OVERWORLD_BIOMES;
    public static GameRules.Key<GameRules.BooleanValue> REMOVE_NETHER_BIOMES;
    public static GameRules.Key<GameRules.BooleanValue> REMOVE_END_BIOMES;

    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_BE_ON_FIRE;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_SPRINT;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_CROUCH;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_SWIM;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_JUMP;
    public static GameRules.Key<GameRules.BooleanValue> PLAYER_CAN_BE_INVISIBLE;

    public static GameRules.Key<GameRules.IntegerValue> REPEATER_BASE_DELAY;
    public static GameRules.Key<GameRules.IntegerValue> REPEATER_SIGNAL;
    public static GameRules.Key<GameRules.IntegerValue> COMPARATOR_BASE_DELAY;
    public static GameRules.Key<GameRules.BooleanValue> COMPARATOR_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> REDSTONE_TORCH_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> REDSTONE_WIRE_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DROPPER_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DISPENSER_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> DAYLIGHT_SENSOR_ENABLED;
    public static GameRules.Key<GameRules.IntegerValue> WOOD_BUTTON_PRESS_DURATION;
    public static GameRules.Key<GameRules.IntegerValue> STONE_BUTTON_PRESS_DURATION;
    public static GameRules.Key<GameRules.BooleanValue> BUTTON_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> LEVER_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> LIGHTNING_ROD_ENABLED;
    public static GameRules.Key<GameRules.IntegerValue> OBSERVER_DELAY;
    public static GameRules.Key<GameRules.IntegerValue> OBSERVER_DURATION;
    public static GameRules.Key<GameRules.BooleanValue> OBSERVER_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PRESSURE_PLATE_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TARGET_BLOCK_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TRAPPED_CHEST_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> TRIPWIRE_HOOK_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> PISTON_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> SCULK_SENSOR_ENABLED;

    public static GameRules.Key<GameRules.BooleanValue> NETHER_PORTALS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> END_PORTALS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> END_GATEWAYS_ENABLED;
    public static GameRules.Key<GameRules.IntegerValue> NETHER_PORTAL_COOLDOWN;
    public static GameRules.Key<GameRules.BooleanValue> CROP_TRAMPLING;
    public static GameRules.Key<GameRules.BooleanValue> OLD_HUNGER;
    public static GameRules.Key<GameRules.BooleanValue> OLD_BOATS;
    public static GameRules.Key<GameRules.BooleanValue> BEACONS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> CONDUITS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> ICE_SLIDING;
    public static GameRules.Key<GameRules.BooleanValue> TOTEMS_ENABLED;
    public static GameRules.Key<GameRules.BooleanValue> BOW_SPAMMING;
    public static GameRules.Key<GameRules.BooleanValue> CROSSBOW_SPAMMING;
    public static GameRules.Key<GameRules.BooleanValue> CREATIVE_SWORD_CAN_BREAK_BLOCKS;
    public static GameRules.Key<GameRules.BooleanValue> PUSHABLE_BUDDING_AMETHYST;
    public static GameRules.Key<GameRules.BooleanValue> CONTAINER_OPENING_BLOCKED;
}
