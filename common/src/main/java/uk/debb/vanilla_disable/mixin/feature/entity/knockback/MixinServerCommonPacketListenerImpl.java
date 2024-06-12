/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.knockback;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ServerCommonPacketListenerImpl.class)
public abstract class MixinServerCommonPacketListenerImpl {
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void vanillaDisable$send(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ClientboundExplodePacket &&
                !(SqlManager.getBoolean("entities", "minecraft:player", "explosion_knockback"))) {
            ci.cancel();
        }
    }
}