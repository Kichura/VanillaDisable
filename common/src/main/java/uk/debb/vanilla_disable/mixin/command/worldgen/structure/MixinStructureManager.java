/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.command.worldgen.structure;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.data.command.CommandDataHandler;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(StructureManager.class)
public abstract class MixinStructureManager {
    @Inject(method = "fillStartsForStructure", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$fillStartsForStructure(Structure structure, LongSet structureRefs, Consumer<StructureStart> startConsumer, CallbackInfo ci) {
        if (CommandDataHandler.structureRegistry == null || CommandDataHandler.server == null) return;
        String rule = Objects.requireNonNull(CommandDataHandler.structureRegistry.getKey(structure)).toString();
        if (!CommandDataHandler.structureMap.isEmpty() && !CommandDataHandler.structureMap.getOrDefault(rule, true)) {
            ci.cancel();
        }
        if (CommandDataHandler.populationDone && !CommandDataHandler.getCachedBoolean("structures", rule, "enabled")) {
            ci.cancel();
        }
    }
}
