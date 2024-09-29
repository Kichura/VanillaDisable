/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.structure;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(StructureManager.class)
public abstract class MixinStructureManager {
    @WrapMethod(method = "fillStartsForStructure")
    private void vanillaDisable$fillStartsForStructure(Structure structure, LongSet structureRefs, Consumer<StructureStart> startConsumer, Operation<Void> original) {
        if (DataDefinitions.structureRegistry != null) {
            String rule = Objects.requireNonNull(DataDefinitions.structureRegistry.getKey(structure)).toString();
            if (!SqlManager.worldgenMaps.get("structures").isEmpty() && !SqlManager.worldgenMaps.get("structures").getOrDefault(rule, true)) {
                return;
            }
            if (DataDefinitions.populationDone && !SqlManager.getBoolean("structures", rule, "enabled")) {
                return;
            }
        }
        original.call(structure, structureRefs, startConsumer);
    }
}
