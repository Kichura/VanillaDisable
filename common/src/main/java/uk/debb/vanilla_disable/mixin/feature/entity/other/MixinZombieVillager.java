/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ZombieVillager.class)
public abstract class MixinZombieVillager {
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$mobInteract(CallbackInfoReturnable<InteractionResult> cir) {
        if (!SqlManager.getBoolean("entities", "minecraft:villager", "can_be_converted_to")) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
