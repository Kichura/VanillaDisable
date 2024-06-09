package uk.debb.vanilla_disable.mixin.command.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin({FixedBiomeSource.class, TheEndBiomeSource.class, MultiNoiseBiomeSource.class, CheckerboardColumnBiomeSource.class})
public abstract class MultipleMixinBiomeSource {
    @ModifyReturnValue(method = "getNoiseBiome*", at = @At("RETURN"))
    private Holder<Biome> vanillaDisable$getNoiseBiome(Holder<Biome> original) {
        return CommandDataHandler.getBiome(original);
    }
}
