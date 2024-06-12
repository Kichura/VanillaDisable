/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.worldgen.structure;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(StructureCheck.class)
public abstract class MixinStructureCheck {
    @Inject(method = "checkStart", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$checkStart(ChunkPos chunkPos, Structure structure, StructurePlacement structurePlacement, boolean bl, CallbackInfoReturnable<StructureCheckResult> cir) {
        if (DataDefinitions.structureRegistry == null || DataDefinitions.server == null) return;
        String rule = Objects.requireNonNull(DataDefinitions.structureRegistry.getKey(structure)).toString();
        if (!SqlManager.structureMap.isEmpty() && !SqlManager.structureMap.getOrDefault(rule, true)) {
            cir.setReturnValue(StructureCheckResult.START_NOT_PRESENT);
        }
        if (DataDefinitions.populationDone && !SqlManager.getBoolean("structures", rule, "enabled")) {
            cir.setReturnValue(StructureCheckResult.START_NOT_PRESENT);
        }
    }
}
