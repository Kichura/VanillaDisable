package uk.debb.vanilla_disable.gamerules.mixin.biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.gamerules.util.gamerules.Gamerules;
import uk.debb.vanilla_disable.gamerules.util.maps.Maps;

import java.util.Set;

@Mixin(BiomeSource.class)
public abstract class MixinBiomeSource implements Maps {
    @ModifyReturnValue(method = "possibleBiomes", at = @At("RETURN"))
    private Set<Holder<Biome>> modifyPossibleBiomes(Set<Holder<Biome>> original) {
        for (Holder<Biome> biomeHolder : original) {
            Gamerules gameRule = biomeSourceBiomeHolderMap.get(biomeHolder);
            if (!Gamerules.BIOMES_ENABLED.getBool() || (gameRule != null && !gameRule.getBool())) {
                original.remove(biomeHolder);
            }
        }
        return original;
    }
}