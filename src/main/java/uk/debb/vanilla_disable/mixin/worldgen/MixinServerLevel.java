package uk.debb.vanilla_disable.mixin.worldgen;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.util.gamerules.GameruleHelper;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel {
    @Inject(method = "makeObsidianPlatform", at = @At("HEAD"), cancellable = true)
    private static void cancelMakingObsidianPlatform(ServerLevel level, CallbackInfo ci) {
        if (!GameruleHelper.getBool(Gamerules.END_FEATURES_GENERATION)) {
            ci.cancel();
        }
    }
}