package dev.hely.tag.profile.storage;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface StorageHook {
    String getTag(UUID player);
    void setTag(UUID player, String tag);
}
