package uk.debb.vanilla_disable.mixin.spawning;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NaturalSpawner.AfterSpawnCallback;
import net.minecraft.world.level.NaturalSpawner.SpawnPredicate;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.gamerules.RegisterGamerules;

@Mixin(NaturalSpawner.class)
public class MixinNaturalSpawner {
    /**
     * @author DragonEggBedrockBreaking
     * @reason map of all spawn groups to their gamerules
     */
    @Unique
    private static final Map<MobCategory, GameRules.Key<GameRules.BooleanValue>> spawnGroupMap = new HashMap<MobCategory, GameRules.Key<GameRules.BooleanValue>>();

    /**
     * @author DragonEggBedrockBreaking
     * @reason the map otherwise initialises before the gamerules are created and always returns null
     */
    @Unique
    private static void addOptionsToMap() {
        spawnGroupMap.put(MobCategory.MONSTER, RegisterGamerules.MONSTER_SPAWNING);
        spawnGroupMap.put(MobCategory.CREATURE, RegisterGamerules.CREATURE_SPAWNING);
        spawnGroupMap.put(MobCategory.AMBIENT, RegisterGamerules.AMBIENT_SPAWNING);
        spawnGroupMap.put(MobCategory.AXOLOTLS, RegisterGamerules.AXOLOTL_SPAWNING);
        spawnGroupMap.put(MobCategory.UNDERGROUND_WATER_CREATURE, RegisterGamerules.GLOWSQUID_SPAWNING);
        spawnGroupMap.put(MobCategory.WATER_AMBIENT, RegisterGamerules.WATER_AMBIENT_SPAWNING);
        spawnGroupMap.put(MobCategory.WATER_CREATURE, RegisterGamerules.WATER_CREATURE_SPAWNING);
    }

    /**
     * @author DragonEggBedrockBreaking
     * @param group the type of entity
     * @param world the world
     * @param chunk the chunk
     * @param checker profiling/testing
     * @param runner profiling/testing
     * @param ci the callback info
     */
    @Inject(method = "spawnCategoryForChunk", at = @At(value = "HEAD"), cancellable = true)
    private static void cancelSpawningCategoryForChunk(MobCategory group, ServerLevel world, LevelChunk chunk, SpawnPredicate checker, AfterSpawnCallback runner, CallbackInfo ci) {
        if (spawnGroupMap.isEmpty()) {
            addOptionsToMap();
        }
        GameRules.Key<GameRules.BooleanValue> gameRule = spawnGroupMap.get(group);
        if (gameRule != null && !RegisterGamerules.getServer().getGameRules().getBoolean(gameRule)) {
            ci.cancel();
        }
    }
}