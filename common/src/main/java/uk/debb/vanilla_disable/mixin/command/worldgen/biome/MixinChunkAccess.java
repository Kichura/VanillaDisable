package uk.debb.vanilla_disable.mixin.command.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(ChunkAccess.class)
public abstract class MixinChunkAccess {
    @ModifyReturnValue(method = "getNoiseBiome", at = @At("RETURN"))
    private Holder<Biome> vanillaDisable$getNoiseBiome(Holder<Biome> original) {
        return CommandDataHandler.getBiome(original);
    }
}
