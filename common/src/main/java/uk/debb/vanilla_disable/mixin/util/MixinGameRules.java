/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import com.mojang.serialization.DynamicLike;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.MigrationHandler;

@Mixin(GameRules.class)
public abstract class MixinGameRules {
    @Inject(method = "loadFromTag", at = @At("HEAD"))
    private void vanillaDisable$loadFromTag(DynamicLike<?> dynamic, CallbackInfo ci) {
        MigrationHandler.dynamic = dynamic;
    }
}
