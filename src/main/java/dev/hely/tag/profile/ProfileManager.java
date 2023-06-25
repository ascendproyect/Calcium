package dev.hely.tag.profile;

import dev.hely.lib.CC;
import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.Mongo;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class ProfileManager {

    private final StorageHook storage;

    public ProfileManager (){
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aLooking for a storage system..."));
        switch (Neon.getInstance().getConfig().getString("settings.storage.drive")){
            case "MONGO":
                storage = new Mongo();
                break;
            case "MYSQL":
                storage = new MySQL();
                break;
            default:
                this.storage = new YML();
                break;
        }
        this.storage.onEnable();
    }


    public void onDisable(){
        this.storage.onDisable();
    }
}
