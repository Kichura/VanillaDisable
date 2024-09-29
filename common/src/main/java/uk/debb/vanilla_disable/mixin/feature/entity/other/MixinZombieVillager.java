/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ZombieVillager.class)
public abstract class MixinZombieVillager {
    @WrapMethod(method = "mobInteract")
    private InteractionResult vanillaDisable$mobInteract(Player player, InteractionHand hand, Operation<InteractionResult> original) {
        if (SqlManager.getBoolean("entities", "minecraft:villager", "can_be_converted_to")) {
            return original.call(player, hand);
        }
        return InteractionResult.FAIL;
    }
}
