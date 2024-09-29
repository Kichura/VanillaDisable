/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.feature;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.EndPlatformFeature;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(EndPlatformFeature.class)
public abstract class MixinEndPlatformFeature {
    @WrapMethod(method = "createEndPlatform")
    private static void vanillaDisable$createEndPlatform(ServerLevelAccessor level, BlockPos pos, boolean dropBlocks, Operation<Void> original) {
        if (SqlManager.getBoolean("placed_features", "minecraft:end_platform", "enabled")) {
            original.call(level, pos, dropBlocks);
        }
    }
}
