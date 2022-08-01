package uk.debb.vanilla_disable.mixin.items;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;
import uk.debb.vanilla_disable.util.maps.Maps;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements Maps {
    @Shadow
    public abstract Item getItem();

    @ModifyReturnValue(method = "use", at = @At("RETURN"))
    private InteractionResultHolder<ItemStack> cancelUsage(InteractionResultHolder<ItemStack> original, Level level, Player player, InteractionHand interactionHand) {
        Gamerules gameRule = itemStackClassMap.get(this.getItem().getClass());
        if (gameRule != null && !gameRule.getValue(Boolean::parseBoolean)) {
            return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
        }
        return original;
    }

    @ModifyReturnValue(method = "useOn", at = @At("RETURN"))
    private InteractionResult cancelUsageOn(InteractionResult original) {
        Gamerules gameRule = itemStackClassMap.get(this.getItem().getClass());
        if (gameRule != null && !gameRule.getValue(Boolean::parseBoolean)) {
            return InteractionResult.FAIL;
        }
        return original;
    }

    @ModifyReturnValue(method = "interactLivingEntity", at = @At("RETURN"))
    private InteractionResult cancelLivingEntityInteraction(InteractionResult original) {
        Gamerules gameRule = itemStackClassMap.get(this.getItem().getClass());
        if (gameRule != null && !gameRule.getValue(Boolean::parseBoolean)) {
            return InteractionResult.FAIL;
        }
        return original;
    }

    @ModifyReturnValue(method = "finishUsingItem", at = @At("RETURN"))
    private ItemStack cancelItemUseFinishing(ItemStack original) {
        Gamerules gameRule = itemStackClassMap.get(this.getItem().getClass());
        if (gameRule != null && !gameRule.getValue(Boolean::parseBoolean)) {
            return ItemStack.EMPTY;
        }
        return original;
    }
}