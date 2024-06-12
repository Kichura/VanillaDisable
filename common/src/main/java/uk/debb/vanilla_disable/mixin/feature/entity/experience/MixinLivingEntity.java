/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.experience;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$dropExperience(CallbackInfo ci) {
        String entity = DataUtils.getKeyFromEntityTypeRegistry(((LivingEntity) (Object) this).getType());
        if (!SqlManager.getBoolean("entities", entity, "can_drop_xp")) {
            ci.cancel();
        }
    }
}
