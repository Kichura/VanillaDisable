package uk.debb.vanilla_disable.mixin.food.hunger;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(FoodData.class)
public abstract class MixinFoodData {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"
            )
    )
    private Difficulty getWrongDifficulty(Difficulty original) {
        if (Gamerules.OLD_HUNGER.getValue(Boolean::parseBoolean)) {
            Gamerules.setBoolean(GameRules.RULE_NATURAL_REGENERATION, false);
            return Difficulty.PEACEFUL;
        }
        return original;
    }
}