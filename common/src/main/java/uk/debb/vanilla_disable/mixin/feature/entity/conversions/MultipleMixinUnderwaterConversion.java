/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.conversions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin({Husk.class, Zombie.class})
public abstract class MultipleMixinUnderwaterConversion {
    @WrapMethod(method = "doUnderWaterConversion")
    private void vanillaDisable$doUnderWaterConversion(Operation<Void> original) {
        String entity = ((Entity) (Object) this).getType().equals(EntityType.HUSK) ? "minecraft:zombie" : "minecraft:drowned";
        if (SqlManager.getBoolean("entities", entity, "can_be_converted_to")) {
            original.call();
        }
    }
}
