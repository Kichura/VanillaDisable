/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.feature.entity.other;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.DataUtils;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Objects;

@Mixin(VillagerData.class)
public abstract class MixinVillagerData {
    @Shadow
    @Final
    private VillagerType type;
    @Shadow
    @Final
    private VillagerProfession profession;

    @ModifyReturnValue(method = "getType", at = @At("RETURN"))
    private VillagerType vanillaDisable$getType(VillagerType original) {
        if (SqlManager.isConnectionNull()) return original;
        if (DataDefinitions.villagerTypeRegistry.getKey(this.type) == null) return original;
        if (!SqlManager.getBoolean("entities", "minecraft:villager",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.villagerTypeRegistry.getKey(this.type))) + "_type")) {
            return VillagerType.PLAINS;
        }
        return original;
    }

    @ModifyReturnValue(method = "getProfession", at = @At("RETURN"))
    private VillagerProfession vanillaDisable$getProfession(VillagerProfession original) {
        if (SqlManager.isConnectionNull()) return original;
        if (DataDefinitions.villagerProfessionRegistry.getKey(this.profession) == null) return original;
        if (!SqlManager.getBoolean("entities", "minecraft:villager",
                DataUtils.lightCleanup(Objects.requireNonNull(DataDefinitions.villagerProfessionRegistry.getKey(this.profession))) + "_profession")) {
            return VillagerProfession.NONE;
        }
        return original;
    }
}
