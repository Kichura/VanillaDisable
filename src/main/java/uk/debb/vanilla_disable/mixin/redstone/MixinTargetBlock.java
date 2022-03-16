package uk.debb.vanilla_disable.mixin.redstone;

import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.gamerules.RegisterGamerules;

@Mixin(TargetBlock.class)
public abstract class MixinTargetBlock {
    /**
     * @author DragonEggBedrockBreaking
     * @reason edit the signal strength outputted by the redstone component
     * @param blockHitResult the result of hitting the block
     * @param vec3 the vec3 position/data
     * @param cir the returnable callback info
     */
    @Inject(method = "getRedstoneStrength", at = @At("HEAD"), cancellable = true)
    private static void modifyRedstoneStrength(BlockHitResult blockHitResult, Vec3 vec3, CallbackInfoReturnable<Integer> cir) {
        if (RegisterGamerules.getServer() == null) return;
        if (!RegisterGamerules.getServer().getGameRules().getBoolean(RegisterGamerules.TARGET_BLOCK_ENABLED)) {
            cir.setReturnValue(0);
        }
    }
}