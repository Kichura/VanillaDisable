/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.experience;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @WrapMethod(method = "dropExperience")
    private void vanillaDisable$dropExperience(ServerLevel serverLevel, Entity entity, Operation<Void> original) {
        String entity_str = DataUtils.getKeyFromEntityTypeRegistry(((LivingEntity) (Object) this).getType());
        if (SqlManager.getBoolean("entities", entity_str, "can_drop_xp")) {
            original.call(serverLevel, entity);
        }
    }
}
