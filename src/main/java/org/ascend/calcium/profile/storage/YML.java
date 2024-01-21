package org.ascend.calcium.profile.storage;

import org.ascend.calcium.Calcium;
import org.ascend.calcium.profile.StorageHook;
import dev.hely.lib.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class YML implements StorageHook {

    private final Map<UUID, String> tag;
    private final Map<UUID, List<String>> favourites;

    public YML(){
        this.tag = new ConcurrentHashMap<>();
        this.favourites = new ConcurrentHashMap<>();
    }

    public Map<UUID, String> getTag() {
        return this.tag;
    }

    public Map<UUID, List<String>> getFavourites() {
        return this.favourites;
    }

    @Override
    public String getTag(UUID player) {
        if(getTag().containsKey(player)) return getTag().get(player);
        return "";
    }

    @Override
    public List<String> getFavourites(UUID player) {
        if (getFavourites().containsKey(player)) {
            return getFavourites().get(player);
        } else {
            return Arrays.asList("");
        }
    }

    @Override
    public void setTag(UUID uuid, String tags) {
        this.tag.put(uuid, tags);
    }

    @Override
    public void setFavourites(UUID player, List<String> favourites) {
        this.favourites.put(player, favourites);
    }

    @Override
    public void onEnable() {
        for(String player: Calcium.getInstance().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
            UUID uuid = UUID.fromString(player);
            String tags = Calcium.getInstance().getProfileConfig().getConfig().getString("profile." + player + ".tag");
            this.setTag(uuid, tags);
            List<String> favourites = Calcium.getInstance().getProfileConfig().getConfig().getStringList("profile." + player + ".favourites");
            this.setFavourites(uuid, favourites);
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Calcium] &aYou have successfully connected to the default &6YAML Storage Database&a!"));
    }

    @Override
    public void onDisable() {
        FileConfiguration config = Calcium.getInstance().getProfileConfig().getConfig();
        for (Map.Entry<UUID, String> entry : this.getTag().entrySet()) {
            config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
        }
        for (Map.Entry<UUID, List<String>> entry : this.getFavourites().entrySet()) {
            config.set("profile." + entry.getKey().toString() + ".favourites", entry.getValue());
        }
        Calcium.getInstance().getProfileConfig().save();
    }
}
