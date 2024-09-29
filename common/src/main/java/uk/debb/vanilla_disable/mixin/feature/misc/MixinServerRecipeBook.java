/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.misc;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ServerRecipeBook.class)
public class MixinServerRecipeBook {
    @WrapMethod(method = "loadRecipes")
    private void vanillaDisable$loadRecipes(ListTag tags, Consumer<RecipeHolder<?>> recipeConsumer, RecipeManager recipeManager, Operation<Void> original) {
        if (SqlManager.getBoolean("misc", "recipe_book", "enabled")) {
            original.call(tags, recipeConsumer, recipeManager);
        }
    }

    @WrapMethod(method = "sendRecipes")
    private void vanillaDisable$sendRecipes(ClientboundRecipePacket.State state, ServerPlayer player, List<ResourceLocation> recipes, Operation<Void> original) {
        if (SqlManager.getBoolean("misc", "recipe_book", "enabled")) {
            original.call(state, player, recipes);
        }
    }
}
