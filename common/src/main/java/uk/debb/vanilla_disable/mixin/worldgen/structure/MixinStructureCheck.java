/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.worldgen.structure;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.debb.vanilla_disable.data.worldgen.WorldgenDataHandler;

import java.util.Objects;

@Mixin(StructureCheck.class)
public abstract class MixinStructureCheck {
    @Inject(method = "checkStart", at = @At("HEAD"), cancellable = true)
    private void vanillaDisable$checkStart(ChunkPos chunkPos, Structure structure, StructurePlacement structurePlacement, boolean bl, CallbackInfoReturnable<StructureCheckResult> cir) {
        String rule = WorldgenDataHandler.cleanup(Objects.requireNonNull(WorldgenDataHandler.structureRegistry.getKey(structure)));
        if (!WorldgenDataHandler.get("structures", rule)) {
            cir.setReturnValue(StructureCheckResult.START_NOT_PRESENT);
        }
    }
}
