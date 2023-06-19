package uk.debb.vanilla_disable.command.mixin.rule.item.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.command.data.CommandDataHandler;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {
    @ModifyReturnValue(method = "isFuel", at = @At("RETURN"))
    private static boolean isFuel(boolean original, ItemStack itemStack) {
        String item = CommandDataHandler.getKeyFromItemRegistry(itemStack.getItem());
        if (CommandDataHandler.getCachedInt("items", item, "fuel_duration") <= 0) {
            return false;
        }
        return original;
    }

    @ModifyReturnValue(method = "getBurnDuration", at = @At("RETURN"))
    private int getBurnDuration(int original, ItemStack itemStack) {
        String item = CommandDataHandler.getKeyFromItemRegistry(itemStack.getItem());
        return CommandDataHandler.getCachedInt("items", item, "fuel_duration");
    }
}