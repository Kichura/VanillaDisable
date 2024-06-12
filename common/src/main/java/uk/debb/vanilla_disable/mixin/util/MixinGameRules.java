/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.util;

import com.mojang.serialization.DynamicLike;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(GameRules.class)
public abstract class MixinGameRules {

    @Inject(method = "loadFromTag", at = @At("HEAD"))
    private void vanillaDisable$loadFromTag(DynamicLike<?> dynamic, CallbackInfo ci) {
        dynamic.get("raidWavesEasy").asString().result().ifPresent(s ->
                SqlManager.legacyGameruleMap.put("raid_waves_easy", ObjectList.of("raid_waves", s)));
        dynamic.get("raidWavesNormal").asString().result().ifPresent(s ->
                SqlManager.legacyGameruleMap.put("raid_waves_normal", ObjectList.of("raid_waves", s)));
        dynamic.get("raidWavesHard").asString().result().ifPresent(s ->
                SqlManager.legacyGameruleMap.put("raid_waves_hard", ObjectList.of("raid_waves", s)));
        dynamic.get("recipeBookEnabled").asString().result().ifPresent(s ->
                SqlManager.legacyGameruleMap.put("recipe_book", ObjectList.of("enabled", s)));
    }
}
