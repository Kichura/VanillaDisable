/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @ModifyReturnValue(method = "isInvulnerableTo", at = @At(value = "RETURN"))
    private boolean vanillaDisable$isInvulnerableTo(boolean original, DamageSource source) {
        return original || !SqlManager.getBoolean("entities", "minecraft:player",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.damageTypeRegistry.getKey(source.type()))) + "_damage");
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "attack", at = @At("RETURN"))
    private void vanillaDisable$attack(Entity target, CallbackInfo ci) {
        boolean hasFireAspect = ((Player) (Object) this).getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                .keySet().stream().anyMatch(enchantment -> enchantment.is(Enchantments.FIRE_ASPECT.location()));
        if (target instanceof Creeper creeper && hasFireAspect &&
                SqlManager.getBoolean("entities", "minecraft:creeper", "can_be_lit_by_fire_aspect")) {
            creeper.ignite();
        }
    }

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$interactOn(Entity entityToInteractOn, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        String entityType = DataUtils.getKeyFromEntityTypeRegistry(entityToInteractOn.getType());
        if (!SqlManager.getBoolean("entities", entityType, "can_player_interact")) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
