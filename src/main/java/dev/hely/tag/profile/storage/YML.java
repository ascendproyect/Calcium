package dev.hely.tag.profile.storage;

import dev.hely.tag.profile.StorageHook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class YML implements StorageHook {

    private static Map<UUID, String> tag;

    public YML(){
        tag = new HashMap<>();
    }

    public static Map<UUID, String> getTag() {
        return tag;
    }

    @Override
    public String getTag(UUID player) {
        if(getTag().containsKey(player)) return getTag().get(player);
        return "";
    }

    @Override
    public void setTag(UUID uuid, String tags) {
        tag.put(uuid, tags);
    }
}
