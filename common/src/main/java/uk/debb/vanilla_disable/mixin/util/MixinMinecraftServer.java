/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "createLevels", at = @At("RETURN"))
    private void vanillaDisable$createLevels(CallbackInfo ci) {
        DataHandler.server = (MinecraftServer) (Object) this;
        DataHandler.populateRegistries();
        if (!DataHandler.populationDone) {
            DataHandler.populate();
        }
        DataHandler.handleDatabase();
    }

    @Inject(method = "stopServer", at = @At("TAIL"))
    private void vanillaDisable$stopServer(CallbackInfo ci) {
        DataHandler.closeConnection();
    }
}
