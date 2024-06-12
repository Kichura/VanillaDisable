/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.VDCommand;

import java.util.concurrent.TimeUnit;

@Mixin(Commands.class)
public abstract class MixinCommands {
    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void vanillaDisable$init(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
        new Thread(() -> {
            while (!DataDefinitions.populationDone) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
            new VDCommand(this.dispatcher).init();
        }).start();
    }
}
