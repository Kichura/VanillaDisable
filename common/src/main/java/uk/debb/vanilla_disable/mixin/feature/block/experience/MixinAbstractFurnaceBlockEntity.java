/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.experience;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.List;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {
    @WrapMethod(method = "getRecipesToAwardAndPopExperience")
    private List<RecipeHolder<?>> vanillaDisable$getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec, Operation<List<RecipeHolder<?>>> original) {
        String block = DataUtils.getKeyFromBlockRegistry(((BlockEntity) (Object) this).getBlockState().getBlock());
        if (SqlManager.getBoolean("blocks", block, "can_drop_xp")) {
            return original.call(level, popVec);
        }
        return new ObjectArrayList<>();
    }
}
