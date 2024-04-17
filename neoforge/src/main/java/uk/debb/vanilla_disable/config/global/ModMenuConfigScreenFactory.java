/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.global;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler.ConfigScreenFactory;
import uk.debb.vanilla_disable.Constants;

@Mod(Constants.MOD_ID)
public class ModMenuConfigScreenFactory {
    public ModMenuConfigScreenFactory() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((minecraft, screen) -> new VanillaDisableConfigScreen(screen)));
    }
}