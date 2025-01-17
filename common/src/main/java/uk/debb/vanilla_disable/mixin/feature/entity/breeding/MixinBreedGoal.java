/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.breeding;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(BreedGoal.class)
public abstract class MixinBreedGoal {
    @Shadow
    @Final
    protected Animal animal;

    @ModifyReturnValue(method = "canUse", at = @At(value = "RETURN"))
    private boolean vanillaDisable$canUse(boolean original) {
        String entity = DataUtils.getKeyFromEntityTypeRegistry(this.animal.getType());
        return original && SqlManager.getBoolean("entities", entity, "can_breed");
    }

    @ModifyReturnValue(method = "canContinueToUse", at = @At(value = "RETURN"))
    private boolean vanillaDisable$canContinueToUse(boolean original) {
        String entity = DataUtils.getKeyFromEntityTypeRegistry(this.animal.getType());
        return original && SqlManager.getBoolean("entities", entity, "can_breed");
    }
}
