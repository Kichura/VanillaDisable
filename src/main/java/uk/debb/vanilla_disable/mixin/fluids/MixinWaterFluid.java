package uk.debb.vanilla_disable.mixin.fluids;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(WaterFluid.class)
public abstract class MixinWaterFluid {
    @ModifyReturnValue(method = "getDropOff", at = @At("RETURN"))
    private int getWaterDropOff(int original, LevelReader world) {
        if (world instanceof Level) {
            return Gamerules.WATER_REACHES_FAR.getBool() ? 1 : 2;
        }
        return original;
    }

    @ModifyReturnValue(method = "getTickDelay", at = @At("RETURN"))
    private int getWaterTickDelay(int original, LevelReader world) {
        if (world instanceof Level) {
            return Gamerules.WATER_FLOW_SPEED.getInt();
        }
        return original;
    }

    @ModifyReturnValue(method = "canConvertToSource", at = @At("RETURN"))
    private boolean canWaterConvertToSource(boolean original) {
        return Gamerules.INFINITE_WATER.getBool();
    }
}