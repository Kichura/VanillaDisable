package uk.debb.vanilla_disable.mixin.knockback;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.util.Gamerules;
import uk.debb.vanilla_disable.util.VDServer;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {
    @Shadow public ServerPlayer player;

    /**
     * @author DragonEggBedrockBreaking
     * @reason Disable all explosion knockback if explosion knockback is disabled
     * @param packet the packet
     * @param ci the callback info
     */
    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packet, CallbackInfo ci) {
        if (VDServer.getServer() == null) return;
        if (packet instanceof ClientboundExplodePacket &&
            !(this.player.getLevel().getGameRules().getBoolean(Gamerules.KNOCKBACK_ENABLED) &&
              this.player.getLevel().getGameRules().getBoolean(Gamerules.EXPLOSION_KNOCKBACK))) {
            ci.cancel();
        }
    }
}