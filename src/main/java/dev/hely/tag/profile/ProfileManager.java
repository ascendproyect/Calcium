package dev.hely.tag.profile;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileManager {


    public ProfileManager(){
        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")) new YML();
    }

    public void onDisable(){

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")){
            YML yml = new YML();
            yml.onSave();
        }

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("MYSQL")){
            MySQL.disconnect();
        }
    }
}
