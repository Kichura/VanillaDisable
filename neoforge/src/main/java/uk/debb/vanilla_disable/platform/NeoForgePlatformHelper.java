package uk.debb.vanilla_disable.platform;

import net.neoforged.fml.loading.LoadingModList;
import uk.debb.vanilla_disable.platform.services.IPlatformHelper;

import java.util.Optional;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public boolean isModLoaded(String modId) {
        return Optional.ofNullable(LoadingModList.get()).map(ml -> ml.getModFileById(modId)).isPresent();
    }
}
