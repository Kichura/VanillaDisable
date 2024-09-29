/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.item.function;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @ModifyExpressionValue(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"
            )
    )
    private Item vanillaDisable$getItem(Item original) {
        String item = DataUtils.getKeyFromItemRegistry(original);
        if (!SqlManager.getBoolean("items", item, "works")) {
            return Items.AIR;
        }
        return original;
    }

    @WrapMethod(method = "useOn")
    private InteractionResult vanillaDisable$useOn(UseOnContext context, Operation<InteractionResult> original) {
        String item = DataUtils.getKeyFromItemRegistry(context.getItemInHand().getItem());
        if (!SqlManager.getBoolean("items", item, "works")) {
            return InteractionResult.FAIL;
        }
        return original.call(context);
    }
}
