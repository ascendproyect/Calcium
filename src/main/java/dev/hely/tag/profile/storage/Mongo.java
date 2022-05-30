package dev.hely.tag.profile.storage;

import dev.hely.tag.profile.StorageHook;

import java.util.UUID;

public class Mongo implements StorageHook {
    @Override
    public String getTag(UUID player) {
        return null;
    }

    @Override
    public void setTag(UUID player, String tag) {

    }
}
