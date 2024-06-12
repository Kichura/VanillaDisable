/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel {
    @ModifyReturnValue(method = "shouldDiscardEntity", at = @At("RETURN"))
    private boolean vanillaDisable$shouldDiscardEntity(boolean original, Entity entity) {
        String entityName = DataUtils.getKeyFromEntityTypeRegistry(entity.getType());
        return original || !SqlManager.getBoolean("entities", entityName, "can_exist");
    }
}
