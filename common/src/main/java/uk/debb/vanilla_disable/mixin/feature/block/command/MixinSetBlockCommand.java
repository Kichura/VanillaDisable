/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.function.Predicate;

@Mixin(SetBlockCommand.class)
public abstract class MixinSetBlockCommand {
    @Inject(method = "setBlock", at = @At("HEAD"))
    private static void vanillaDisable$setBlock(CommandSourceStack source, BlockPos pos, BlockInput state, SetBlockCommand.Mode mode, @Nullable Predicate<BlockInWorld> predicate, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        String block = DataUtils.getKeyFromBlockRegistry(state.getState().getBlock());
        if (!SqlManager.getBoolean("blocks", block, "can_be_placed_by_command")) {
            throw new SimpleCommandExceptionType(Component.translatable("vd.commands.setblock.disabled")).create();
        }
    }
}
