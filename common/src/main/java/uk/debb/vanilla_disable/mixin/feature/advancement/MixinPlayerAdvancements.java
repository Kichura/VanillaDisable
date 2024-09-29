/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.advancement;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.commands.AdvancementCommands;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(PlayerAdvancements.class)
public abstract class MixinPlayerAdvancements {
    @WrapMethod(method = "award")
    private boolean vanillaDisable$award(AdvancementHolder advancement, String criterionKey, Operation<Boolean> original) {
        String adv = advancement.id().toString();
        if (adv.contains("recipe") || SqlManager.getBoolean("advancements", adv, "enabled")) {
            original.call(advancement, criterionKey);
        } else if (Thread.currentThread().getStackTrace()[5].getClassName().equals(AdvancementCommands.class.getName())) {
            DataDefinitions.server.getPlayerList().broadcastSystemMessage(Component.translatable("vd.advancements.disabled.by.vd").withStyle(ChatFormatting.RED), false);
        }
        return false;
    }
}
