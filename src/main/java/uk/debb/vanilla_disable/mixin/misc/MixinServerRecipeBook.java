package uk.debb.vanilla_disable.mixin.misc;

import net.minecraft.stats.ServerRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.util.gamerules.BooleanGamerules;
import uk.debb.vanilla_disable.util.gamerules.GameruleHelper;

@Mixin(ServerRecipeBook.class)
public class MixinServerRecipeBook {
    @Inject(method = "loadRecipes", at = @At("HEAD"), cancellable = true)
    private void cancelLoadingRecipes(CallbackInfo ci) {
        if (!GameruleHelper.getBool(BooleanGamerules.RECIPE_BOOK_ENABLED)) {
            ci.cancel();
        }
    }

    @Inject(method = "sendRecipes", at = @At("HEAD"), cancellable = true)
    private void cancelSendingRecipes(CallbackInfo ci) {
        if (!GameruleHelper.getBool(BooleanGamerules.RECIPE_BOOK_ENABLED)) {
            ci.cancel();
        }
    }
}
