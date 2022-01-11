package uk.debb.vanilla_disable.mixin.despawning;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.gamerules.RegisterGamerules;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity extends LivingEntity implements Bucketable {
    protected MixinMobEntity(EntityType<? extends MobEntity> entityType, World world) {
        super((EntityType<? extends LivingEntity>)entityType, world);
    }
    
    @Shadow private boolean persistent;

    /**
     * @author DragonEggBedrockBreaking
     * @reason map of many mob groups to their gamerules
     */
    private static final Map<Class<?>, GameRules.Key<GameRules.BooleanRule>> spawnGroupDespawnMap = new HashMap<Class<?>, GameRules.Key<GameRules.BooleanRule>>();

    /**
     * @author DragonEggBedrockBreaking
     * @reason the map otherwise initialises before the gamerules are created and always returns null
     */
    private void addOptionsToMap() {
        spawnGroupDespawnMap.put(HostileEntity.class, RegisterGamerules.MONSTERS_DESPAWN);
        spawnGroupDespawnMap.put(AnimalEntity.class, RegisterGamerules.CREATURES_DESPAWN);
        spawnGroupDespawnMap.put(AmbientEntity.class, RegisterGamerules.AMBIENT_DESPAWN);
        spawnGroupDespawnMap.put(AxolotlEntity.class, RegisterGamerules.AXOLOTLS_DESPAWN);
        spawnGroupDespawnMap.put(GlowSquidEntity.class, RegisterGamerules.GLOWSQUIDS_DESPAWN);
        spawnGroupDespawnMap.put(FishEntity.class, RegisterGamerules.WATER_AMBIENT_DESPAWN);
        spawnGroupDespawnMap.put(WaterCreatureEntity.class, RegisterGamerules.WATER_CREATURES_DESPAWN);
    }
    /**
     * @author DragonEggBedrockBreaking
     * @reason Fish and axolotls have additional restrictions
     * @return Whether the additional restrictions are met
     */
    private boolean additionalRestrictionsMet() {
        MobEntity entity = (MobEntity) (Object) this;
        if (entity instanceof AxolotlEntity || entity instanceof FishEntity) {
            return !this.hasCustomName() && !this.isFromBucket();
        }
        return true;
    }

    /**
     * @author DragonEggBedrockBreaking
     * @reason Change whether the mob despawns based on gamerules
     */
    @Inject(method = "canImmediatelyDespawn", at = @At("HEAD"), cancellable = true)
    private void cannotImmediatelyDespawn(double distanceSquared, CallbackInfoReturnable<Boolean> cir) {
        if (spawnGroupDespawnMap.isEmpty()) {
            this.addOptionsToMap();
        }
        GameRules.Key<GameRules.BooleanRule> gameRule = spawnGroupDespawnMap.get(this.getClass());
        if (gameRule != null && RegisterGamerules.getServer().getGameRules().getBoolean(gameRule)) {
            cir.setReturnValue(this.additionalRestrictionsMet());
        }
    }
}