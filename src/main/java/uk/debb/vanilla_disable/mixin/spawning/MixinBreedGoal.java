package uk.debb.vanilla_disable.mixin.spawning;

import net.minecraft.world.entity.ai.goal.BreedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.util.Gamerules;
import uk.debb.vanilla_disable.util.VDServer;

@Mixin(BreedGoal.class)
public abstract class MixinBreedGoal {
    /**
     * @author DragonEggBedrockBreaking
     * @reason Don't allow breeding to start
     * @param cir Returnable callback info (Boolean)
     */
    @Inject(method = "canUse", at = @At(value = "HEAD"), cancellable = true)
    private void cannotUse(CallbackInfoReturnable<Boolean> cir) {
        if (VDServer.getServer() == null) return;
        if (!VDServer.getServer().getGameRules().getBoolean(Gamerules.ANIMAL_BREEDING)) {
            cir.setReturnValue(false);
        }
    }

    /**
     * @author DragonEggBedrockBreaking
     * @reason Don't allow breeding to continue
     * @param cir Returnable callback info (Boolean)
     */
    @Inject(method = "canContinueToUse", at = @At(value = "HEAD"), cancellable = true)
    private void shouldContinueToUse(CallbackInfoReturnable<Boolean> cir) {
        if (VDServer.getServer() == null) return;
        if (!VDServer.getServer().getGameRules().getBoolean(Gamerules.ANIMAL_BREEDING)) {
            cir.setReturnValue(false);
        }
    }
}
