/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.breeding;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(TemptGoal.class)
public abstract class MixinTemptGoal {
    @Shadow
    @Final
    protected PathfinderMob mob;

    @ModifyReturnValue(method = "shouldFollow", at = @At("RETURN"))
    private boolean shouldFollow(boolean original, LivingEntity livingEntity) {
        if (DataHandler.isConnectionNull()) return original;
        String entity = DataHandler.getKeyFromEntityTypeRegistry(this.mob.getType());
        Item mainHandItem = livingEntity.getMainHandItem().getItem();
        Item offHandItem = livingEntity.getOffhandItem().getItem();
        return DataHandler.getCachedBreedingItems(entity).stream().anyMatch(itemStack -> itemStack.is(mainHandItem) || itemStack.is(offHandItem));
    }
}
