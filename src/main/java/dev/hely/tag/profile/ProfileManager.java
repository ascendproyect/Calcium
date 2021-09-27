package dev.hely.tag.profile;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileManager {


    public ProfileManager(){
        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")){
            new YML();
        }

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("MYSQL")){
            MySQL.connect();
            MySQL.createDatabase();
        }
    }


    public String getTag(Player player){

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")) {

            if(YML.getTag().containsKey(player.getUniqueId())) {
                return YML.getTag().get(player.getUniqueId());
            }
            return "";
        }else if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("MYSQL")){
            return MySQL.getTag(player);
        }
        return "Contact Developers";
    }

    public void setTag(UUID player, String tag){

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("YML")){
            YML.setTag(player, tag);
        }else

        if(Neon.getPlugin().getConfig().getString("settings.storage.drive").equalsIgnoreCase("MYSQL")){
            MySQL.setTag(player, tag);
        }else {
            Bukkit.getConsoleSender().sendMessage("Contact Developers");
        }
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
