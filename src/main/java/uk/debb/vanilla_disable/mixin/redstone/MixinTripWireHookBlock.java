package uk.debb.vanilla_disable.mixin.redstone;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.TripWireHookBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(TripWireHookBlock.class)
public abstract class MixinTripWireHookBlock {
    @ModifyReturnValue(method = "getSignal", at = @At("RETURN"))
    private int modifySignal(int original) {
        if (!Gamerules.TRIPWIRE_HOOK_ENABLED.getValue(Boolean::parseBoolean)) {
            return 0;
        }
        return original;
    }

    @ModifyReturnValue(method = "getDirectSignal", at = @At("RETURN"))
    private int modifyDirectSignal(int original) {
        if (!Gamerules.TRIPWIRE_HOOK_ENABLED.getValue(Boolean::parseBoolean)) {
            return 0;
        }
        return original;
    }
}