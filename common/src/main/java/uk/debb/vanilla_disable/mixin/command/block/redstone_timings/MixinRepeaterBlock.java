/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.block.redstone_timings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

@Mixin(RepeaterBlock.class)
public abstract class MixinRepeaterBlock {
    @Shadow
    @Final
    public static IntegerProperty DELAY;

    @ModifyReturnValue(method = "getDelay", at = @At("RETURN"))
    private int vanillaDisable$getDelay(int original, BlockState state) {
        return state.getValue(DELAY) * CommandDataHandler.getCachedInt("blocks", "minecraft:repeater", "redstone_delay");
    }
}
