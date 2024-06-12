/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.other;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Collection;

@Mixin(GiveCommand.class)
public abstract class MixinGiveCommand {
    @Inject(method = "giveItem", at = @At("HEAD"))
    private static void vanillaDisable$giveItem(CommandSourceStack source, ItemInput item, Collection<ServerPlayer> targets, int count, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        String name = DataUtils.getKeyFromItemRegistry(item.getItem());
        if (!SqlManager.getBoolean("items", name, "can_be_given_by_command")) {
            throw new SimpleCommandExceptionType(Component.translatable("vd.commands.give.disabled")).create();
        }
    }
}
