package dev.hely.tag.profile;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    public void onDisable(){
        switch (Neon.getPlugin().getConfig().getString("settings.storage.drive")){
            case "MONGO":
                break;
            case "MYSQL":
                MySQL.disconnect();
                break;
            default:
                FileConfiguration config = Neon.getPlugin().getProfileConfig().getConfig();
                for (Map.Entry<UUID, String> entry : YML.getTag().entrySet()) {
                    config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
                }
                break;
        }
    }
}
