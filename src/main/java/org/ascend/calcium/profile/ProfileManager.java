package org.ascend.calcium.profile;

import org.ascend.calcium.Calcium;
import dev.hely.lib.CC;
import org.ascend.calcium.profile.storage.Mongo;
import org.ascend.calcium.profile.storage.MySQL;
import org.ascend.calcium.profile.storage.YML;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class ProfileManager {

    private final StorageHook storage;

    public ProfileManager (){
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aLooking for a storage system..."));
        switch (Calcium.getInstance().getConfig().getString("settings.storage.drive")){
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
