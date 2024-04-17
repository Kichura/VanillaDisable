/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.gamerule;

import net.minecraft.stats.ServerRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.data.gamerule.VDGamerules;

@Mixin(ServerRecipeBook.class)
public class MixinServerRecipeBook {
    @Inject(method = "loadRecipes", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$loadRecipes(CallbackInfo ci) {
        if (VDGamerules.server == null) return;
        if (!VDGamerules.server.getGameRules().getBoolean(VDGamerules.RECIPE_BOOK_ENABLED)) {
            ci.cancel();
        }
    }

    @Inject(method = "sendRecipes", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$sendRecipes(CallbackInfo ci) {
        if (VDGamerules.server == null) return;
        if (!VDGamerules.server.getGameRules().getBoolean(VDGamerules.RECIPE_BOOK_ENABLED)) {
            ci.cancel();
        }
    }
}
