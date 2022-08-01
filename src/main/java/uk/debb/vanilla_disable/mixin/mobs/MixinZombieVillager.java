package uk.debb.vanilla_disable.mixin.mobs;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(ZombieVillager.class)
public abstract class MixinZombieVillager {
    @ModifyReturnValue(method = "mobInteract", at = @At("RETURN"))
    private InteractionResult cureMob(InteractionResult original) {
        if (!Gamerules.CURABLE_ZILLAGERS.getValue(Boolean::parseBoolean)) {
            return InteractionResult.CONSUME;
        }
        return original;
    }
}