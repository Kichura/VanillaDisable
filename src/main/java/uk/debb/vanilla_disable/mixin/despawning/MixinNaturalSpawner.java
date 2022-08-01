package uk.debb.vanilla_disable.mixin.despawning;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

import java.util.Objects;

@Mixin(NaturalSpawner.class)
public abstract class MixinNaturalSpawner {
    @ModifyReturnValue(method = "isRightDistanceToPlayerAndSpawnPoint", at = @At("RETURN"))
    private static boolean mayMeRightDistanceToPlayerAndSpawnPoint(boolean original, ServerLevel level, ChunkAccess chunk, BlockPos.MutableBlockPos pos, double squaredDistance) {
        if (squaredDistance <= Math.pow(Gamerules.MIN_SPAWN_DISTANCE.getValue(Double::parseDouble), 2)) {
            return false;
        } else if (level.getSharedSpawnPos().closerToCenterThan(new Vec3((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5), Gamerules.MIN_SPAWN_DISTANCE.getValue(Double::parseDouble))) {
            return false;
        } else {
            return Objects.equals(new ChunkPos(pos), chunk.getPos()) || level.isPositionEntityTicking(pos);
        }
    }
}