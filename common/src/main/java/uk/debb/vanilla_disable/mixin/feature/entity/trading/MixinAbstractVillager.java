/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.trading;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataHandler;

@Mixin(AbstractVillager.class)
public abstract class MixinAbstractVillager {
    @ModifyReceiver(
            method = "notifyTrade",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/trading/MerchantOffer;increaseUses()V"
            ),
            require = 0
    )
    private MerchantOffer vanillaDisable$increaseUses(MerchantOffer receiver) {
        String entity = DataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        if (DataHandler.getCachedBoolean("entities", entity, "can_infinitely_trade")) {
            receiver.resetUses();
        }
        return receiver;
    }

    @ModifyReturnValue(method = "getOffers", at = @At("RETURN"))
    private MerchantOffers vanillaDisable$getOffers(MerchantOffers original) {
        if (DataHandler.isConnectionNull()) return original;
        String entity = DataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        if (!DataHandler.getCachedBoolean("entities", entity, "can_trade")) {
            return new MerchantOffers();
        }
        return original;
    }
}
