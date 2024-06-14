/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.knockback;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow
    private @Nullable LivingEntity lastHurtByMob;

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    public void vanillaDisable$knockback(CallbackInfo ci) {
        String target = DataUtils.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        if (this.lastHurtByMob != null) {
            String source = DataUtils.getKeyFromEntityTypeRegistry(this.lastHurtByMob.getType());
            if (!SqlManager.getBoolean("entities", target, DataUtils.lightCleanup(source) + "_knockback")) {
                ci.cancel();
            }
        }
    }
}