package org.ascend.calcium.profile;

import java.util.List;
import java.util.UUID;

public interface StorageHook {
    String getTag(UUID player);

    List<String> getFavourites(UUID player);

    void setTag(UUID player, String tag);

    void setFavourites(UUID player, List<String> favourites);

    void onEnable();

    void onDisable();
}
