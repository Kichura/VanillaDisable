package uk.debb.vanilla_disable.mixin.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DropperBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.util.gamerules.GameruleHelper;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(DropperBlock.class)
public abstract class MixinDropperBlock {
    @Inject(method = "dispenseFrom", at = @At("HEAD"), cancellable = true)
    private void cancelDispensing(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci) {
        if (!GameruleHelper.getBool(Gamerules.DROPPER_ENABLED)) {
            ci.cancel();
        }
    }
}