package uk.debb.vanilla_disable.command.mixin.rule.entity.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.command.data.CommandDataHandler;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyReturnValue(method = "canBeAffected", at = @At("RETURN"))
    private boolean canBeAffected(boolean original, MobEffectInstance effect) {
        if (CommandDataHandler.isConnectionNull()) return original;
        String entity = CommandDataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        return CommandDataHandler.getCachedBoolean("entities", entity, CommandDataHandler.mobEffectRegistry.getKey(effect.getEffect()) + "_effect");
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V"
            )
    )
    private boolean aiStep(LivingEntity livingEntity) {
        String entity = CommandDataHandler.getKeyFromEntityTypeRegistry(((Entity) (Object) this).getType());
        return CommandDataHandler.getCachedBoolean("entities", entity, "ai");
    }
}