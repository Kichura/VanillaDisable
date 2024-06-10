/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.trading;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(Villager.class)
public abstract class MixinVillager {
    @Shadow
    private int numberOfRestocksToday;
    @Shadow
    private long lastRestockGameTime;

    @SuppressWarnings("ConstantConditions resource")
    @ModifyReturnValue(method = "allowedToRestock", at = @At("RETURN"))
    private boolean vanillaDisable$allowedToRestock(boolean original) {
        int villagerDailyRestocks = DataHandler.getCachedInt("entities", "minecraft:villager", "daily_restocks");
        long restockTimeLimit = 4800L / villagerDailyRestocks;
        return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && ((Entity) (Object) this).level().getGameTime() > this.lastRestockGameTime + restockTimeLimit;
    }
}
