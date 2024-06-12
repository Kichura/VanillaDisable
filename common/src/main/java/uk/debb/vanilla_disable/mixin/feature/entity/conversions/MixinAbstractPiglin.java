/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.conversions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(AbstractPiglin.class)
public abstract class MixinAbstractPiglin {
    @ModifyReturnValue(method = "isImmuneToZombification", at = @At("RETURN"))
    private boolean vanillaDisable$isImmuneToZombification(boolean original) {
        return original || !SqlManager.getBoolean("entities", "minecraft:zombified_piglin", "can_be_converted_to");
    }
}
