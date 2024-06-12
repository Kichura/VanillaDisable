/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.block.placement;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        String dimension = context.getLevel().dimensionType().effectsLocation().toString().replace("the_", "").replace("minecraft:", "");
        String name = "";
        if (this.getItem() instanceof BlockItem blockItem) {
            name = DataUtils.getKeyFromBlockRegistry(blockItem.getBlock());
        } else if (this.getItem() instanceof BucketItem bucketItem) {
            name = DataUtils.getKeyFromItemRegistry(bucketItem).replace("_bucket", "");
            if (name.contains("bucket") || (name.contains("water") && dimension.equals("nether"))) return;
        }
        if (!name.isEmpty()) {
            if (!SqlManager.getBoolean("blocks", name, "can_place_in_" + dimension)) {
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}
