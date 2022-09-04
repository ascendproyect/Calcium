package dev.hely.tag.profile.storage;

import dev.hely.lib.CC;
import dev.hely.tag.Neon;
import dev.hely.tag.profile.StorageHook;
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
        for(String player: Neon.getPlugin().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
            UUID uuid = UUID.fromString(player);
            String tags = Neon.getPlugin().getProfileConfig().getConfig().getString("profile." + player + ".tag");
            this.setTag(uuid, tags);
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to the default YAML system"));
    }

    @Override
    public void onDisable() {
        FileConfiguration config = Neon.getPlugin().getProfileConfig().getConfig();
        for (Map.Entry<UUID, String> entry : this.getTag().entrySet()) {
            config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
        }
        Neon.getPlugin().getProfileConfig().save();
    }
}
