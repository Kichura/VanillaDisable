package uk.debb.vanilla_disable.mixin.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.gamerules.RegisterGamerules;

@Mixin(PistonBaseBlock.class)
public abstract class MixinPistonBaseBlock {
    /**
     * @author DragonEggBedrockBreaking
     * @reason stop pistons extending and retracting
     * @param blockState the state of the block
     * @param level the level
     * @param blockPos the position of the block
     * @param i ???
     * @param j ???
     * @param cir the returnable callback info
     */
    @Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
    private void cancelTriggeringEvent(BlockState blockState, Level level, BlockPos blockPos, int i, int j, CallbackInfoReturnable<Boolean> cir) {
        if (RegisterGamerules.getServer() == null) return;
        if (!RegisterGamerules.getServer().getGameRules().getBoolean(RegisterGamerules.PISTON_ENABLED)) {
            cir.setReturnValue(false);
        }
    }
}