/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.entity.conversions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(Hoglin.class)
public abstract class MixinHoglin {
    @ModifyReturnValue(method = "isImmuneToZombification", at = @At("RETURN"))
    private boolean vanillaDisable$isImmuneToZombification(boolean original) {
        return original || !CommandDataHandler.getCachedBoolean("entities", "minecraft:zombified_piglin", "can_be_converted_to");
    }
}
