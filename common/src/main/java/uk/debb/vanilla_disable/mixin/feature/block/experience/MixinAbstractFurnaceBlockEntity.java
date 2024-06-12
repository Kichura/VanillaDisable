/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.experience;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.List;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {
    @Inject(method = "getRecipesToAwardAndPopExperience", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$getRecipesToAwardAndPopExperience(CallbackInfoReturnable<List<Recipe<?>>> cir) {
        String block = DataUtils.getKeyFromBlockRegistry(((BlockEntity) (Object) this).getBlockState().getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_drop_xp")) {
            cir.setReturnValue(new ObjectArrayList<>());
        }
    }
}
