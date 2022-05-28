package dev.hely.tag.profile;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ProfileManager {


    public ProfileManager(){
        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")) {
            for(String player: Neon.getPlugin().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
                UUID uuid = UUID.fromString(player);
                String tags = Neon.getPlugin().getProfileConfig().getConfig().getString("profile." + player + ".tag");
                Neon.getPlugin().getStorage().setTag(uuid, tags);
            }
        }
    }

    public void onDisable(){
        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")){
            FileConfiguration config = Neon.getPlugin().getProfileConfig().getConfig();
            for (Map.Entry<UUID, String> entry : YML.getTag().entrySet()) {
                config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
            }
            Neon.getPlugin().getProfileConfig().save();
        }else if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("MYSQL")){
            MySQL.disconnect();
        }
    }
}
