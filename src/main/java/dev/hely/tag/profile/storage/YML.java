package dev.hely.tag.profile.storage;

import dev.hely.tag.Neon;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class YML implements StorageHook {

    private static Map<UUID, String> tag;
    public YML(){
        tag = new HashMap<>();
        this.onLoad();
    }

    public Map<UUID, String> getTag() {
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

    public void onLoad(){
        for(String player: Neon.getPlugin().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
            UUID uuid = UUID.fromString(player);
            String tags = Neon.getPlugin().getProfileConfig().getConfig().getString("profile." + player + ".tag");
            this.setTag(uuid, tags);
        }
    }

    public void onSave(){
        FileConfiguration config = Neon.getPlugin().getProfileConfig().getConfig();
        for (Map.Entry<UUID, String> entry : getTag().entrySet()) {
            config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
        }
        Neon.getPlugin().getProfileConfig().save();
    }

}
