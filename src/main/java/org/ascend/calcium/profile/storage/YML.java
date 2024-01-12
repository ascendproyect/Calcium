package org.ascend.calcium.profile.storage;

import org.ascend.calcium.Calcium;
import org.ascend.calcium.profile.StorageHook;
import dev.hely.lib.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class YML implements StorageHook {

    private final Map<UUID, String> tag;

    public YML(){
        this.tag = new ConcurrentHashMap<>();
    }

    public Map<UUID, String> getTag() {
        return this.tag;
    }

    @Override
    public String getTag(UUID player) {
        if(getTag().containsKey(player)) return getTag().get(player);
        return "";
    }

    @Override
    public void setTag(UUID uuid, String tags) {
        this.tag.put(uuid, tags);
    }

    @Override
    public void onEnable() {
        for(String player: Calcium.getInstance().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
            UUID uuid = UUID.fromString(player);
            String tags = Calcium.getInstance().getProfileConfig().getConfig().getString("profile." + player + ".tag");
            this.setTag(uuid, tags);
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to the default YAML system"));
    }

    @Override
    public void onDisable() {
        FileConfiguration config = Calcium.getInstance().getProfileConfig().getConfig();
        for (Map.Entry<UUID, String> entry : this.getTag().entrySet()) {
            config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
        }
        Calcium.getInstance().getProfileConfig().save();
    }
}
