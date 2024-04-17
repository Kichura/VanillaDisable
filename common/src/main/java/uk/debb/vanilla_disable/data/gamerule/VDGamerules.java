/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.data.gamerule;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

public class VDGamerules {
    public static GameRules.Key<GameRules.IntegerValue> RAID_WAVES_EASY;
    public static GameRules.Key<GameRules.IntegerValue> RAID_WAVES_NORMAL;
    public static GameRules.Key<GameRules.IntegerValue> RAID_WAVES_HARD;
    public static GameRules.Key<GameRules.BooleanValue> RECIPE_BOOK_ENABLED;
    public static MinecraftServer server;
}