package org.ascend.calcium.profile;

import java.util.UUID;

public interface StorageHook {

    String getTag(UUID player);

    void setTag(UUID player, String tag);

    void onEnable();

    void onDisable();
}
