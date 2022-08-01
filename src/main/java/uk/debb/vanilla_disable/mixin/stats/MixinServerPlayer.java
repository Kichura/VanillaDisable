package uk.debb.vanilla_disable.mixin.stats;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.debb.vanilla_disable.util.gamerules.Gamerules;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {
    @Unique
    private static final ObjectList<String> generalList = new ObjectArrayList<>() {{
        add("jump");
        add("drop");
        add("bred");
        add("fish_caught");
        add("target_hit");
        add("trade");
    }};

    @Unique
    private boolean shouldCancelStat(Stat<?> stat) {
        if (!Gamerules.STATS_ENABLED.getValue(Boolean::parseBoolean)) {
            return true;
        }
        Registry<?> registry = stat.getType().getRegistry();
        if (registry.equals(Registry.BLOCK)) {
            return !Gamerules.BLOCK_STATS.getValue(Boolean::parseBoolean);
        } else if (registry.equals(Registry.ITEM)) {
            return !Gamerules.ITEM_STATS.getValue(Boolean::parseBoolean);
        } else if (registry.equals(Registry.ENTITY_TYPE)) {
            return !Gamerules.ENTITY_KILL_STATS.getValue(Boolean::parseBoolean);
        }
        StatFormatter formatter = stat.formatter;
        if (formatter.equals(StatFormatter.TIME)) {
            return !Gamerules.TIME_STATS.getValue(Boolean::parseBoolean);
        } else if (formatter.equals(StatFormatter.DISTANCE)) {
            return !Gamerules.DISTANCE_STATS.getValue(Boolean::parseBoolean);
        } else if (formatter.equals(StatFormatter.DIVIDE_BY_TEN)) {
            return !Gamerules.DAMAGE_STATS.getValue(Boolean::parseBoolean);
        }
        String string = stat.getName();
        if (string.contains("interact")) {
            return !Gamerules.GUI_BLOCK_INTERACTION_STATS.getValue(Boolean::parseBoolean);
        }
        for (String section : generalList) {
            if (string.contains(section)) {
                return !Gamerules.GENERAL_STATS.getValue(Boolean::parseBoolean);
            }
        }
        return !Gamerules.GENERAL_BLOCK_INTERACTION_STATS.getValue(Boolean::parseBoolean);
    }

    @Inject(method = "awardStat", at = @At("HEAD"), cancellable = true)
    private void cancelAwardingStat(Stat<?> stat, int i, CallbackInfo ci) {
        if (shouldCancelStat(stat)) {
            ci.cancel();
        }
    }
}
