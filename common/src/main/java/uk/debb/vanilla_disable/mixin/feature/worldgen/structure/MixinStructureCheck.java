/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.structure;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(StructureCheck.class)
public abstract class MixinStructureCheck {
    @WrapMethod(method = "checkStart")
    private StructureCheckResult vanillaDisable$checkStart(ChunkPos chunkPos, Structure structure, StructurePlacement placement, boolean skipKnownStructures, Operation<StructureCheckResult> original) {
        if (DataDefinitions.structureRegistry == null) return null;
        String rule = Objects.requireNonNull(DataDefinitions.structureRegistry.getKey(structure)).toString();
        if (!SqlManager.worldgenMaps.get("structures").isEmpty() && !SqlManager.worldgenMaps.get("structures").getOrDefault(rule, true)) {
            return StructureCheckResult.START_NOT_PRESENT;
        }
        if (DataDefinitions.populationDone && !SqlManager.getBoolean("structures", rule, "enabled")) {
            return StructureCheckResult.START_NOT_PRESENT;
        }
        return original.call(chunkPos, structure, placement, skipKnownStructures);
    }
}
