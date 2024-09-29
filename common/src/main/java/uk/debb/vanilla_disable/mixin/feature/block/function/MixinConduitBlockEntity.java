/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.function;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public abstract class MixinConduitBlockEntity {
    @WrapMethod(method = "applyEffects")
    private static void vanillaDisable$applyEffects(Level level, BlockPos pos, List<BlockPos> positions, Operation<Void> original) {
        if (SqlManager.getBoolean("blocks", "minecraft:conduit", "works")) {
            original.call(level, pos, positions);
        }
    }
}
