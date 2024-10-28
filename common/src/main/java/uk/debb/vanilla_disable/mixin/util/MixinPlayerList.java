package uk.debb.vanilla_disable.mixin.util;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList {
    @WrapWithCondition(
            method = "sendPlayerPermissionLevel(Lnet/minecraft/server/level/ServerPlayer;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/commands/Commands;sendCommands(Lnet/minecraft/server/level/ServerPlayer;)V"
            )
    )
    private boolean vanillaDisable$sendCommands(Commands instance, ServerPlayer player) {
        String placementMethod = Thread.currentThread().getStackTrace()[4].getMethodName();
        return !(placementMethod.equals("teleport") || placementMethod.equals("method_61275"));
    }
}
