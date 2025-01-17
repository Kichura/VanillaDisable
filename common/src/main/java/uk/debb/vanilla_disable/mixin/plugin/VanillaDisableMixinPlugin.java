/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.mixin.plugin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import uk.debb.vanilla_disable.Constants;
import uk.debb.vanilla_disable.platform.Services;

import java.util.List;
import java.util.Set;

public class VanillaDisableMixinPlugin implements IMixinConfigPlugin {
    static MixinPluginConfig config;

    @Override
    public void onLoad(String s) {
        if (config == null) {
            config = new MixinPluginConfig();
            Constants.LOG.info("Loaded VanillaDisable mixin config file with {} override(s).", config.properties.size());
            config.compatibility.forEach((mixin, mod) -> {
                if (Services.PLATFORM.isModLoaded(mod)) {
                    Constants.LOG.warn("Disabled {} due to compatibility with {}.", mixin, mod);
                }
            });
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] mixinPath = mixinClassName.substring(30).split("\\.");
        StringBuilder current = new StringBuilder("mixin");
        for (String part : mixinPath) {
            current.append(".").append(part);
            if (config.isMixinConfigured(current.toString())) {
                return config.isMixinEnabled(current.toString());
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }


    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }
}
