/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.structure;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(StructureManager.class)
public abstract class MixinStructureManager {
    @Inject(method = "fillStartsForStructure", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$fillStartsForStructure(Structure structure, LongSet structureRefs, Consumer<StructureStart> startConsumer, CallbackInfo ci) {
        if (DataDefinitions.structureRegistry == null || DataDefinitions.server == null) return;
        String rule = Objects.requireNonNull(DataDefinitions.structureRegistry.getKey(structure)).toString();
        if (!SqlManager.structureMap.isEmpty() && !SqlManager.structureMap.getOrDefault(rule, true)) {
            ci.cancel();
        }
        if (DataDefinitions.populationDone && !SqlManager.getBoolean("structures", rule, "enabled")) {
            ci.cancel();
        }
    }
}
