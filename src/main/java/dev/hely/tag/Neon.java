package dev.hely.tag;

import com.google.common.collect.ImmutableList;
import dev.hely.lib.command.CommandManager;
import dev.hely.lib.configuration.Config;
import dev.hely.lib.manager.Manager;
import dev.hely.lib.menu.MenuListener;
import dev.hely.tag.menu.edit.EditMenu;
import dev.hely.tag.module.ModuleManager;
import dev.hely.tag.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Neon extends JavaPlugin {

    private static Neon plugin;
    private List<Manager> managerList;
    private Config tags;
    private Config profile;
    private ModuleManager moduleManager;
    private ProfileManager profileManager;

    @Override
    public void onLoad() {
        managerList = ImmutableList.of(
                CommandManager.INSTANCE);
    }
    
    @Override
    public void onEnable() {
        plugin = this;
            this.saveDefaultConfig();
            this.saveConfig();
            this.reloadConfig();
            this.onConfig();
            this.onListener();
            managerList.forEach(manager -> manager.onEnable(this));
            this.moduleManager = new ModuleManager();
            this.profileManager = new ProfileManager();

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new PlaceHolderAPIExpansion().register();
    }

    @Override
    public void onDisable() {
        managerList.forEach(manager -> manager.onDisable(this));
        this.moduleManager.onDisable();
        this.profileManager.onDisable();
        plugin = null;
    }

    public static Neon getPlugin(){
        return plugin;
    }

    public void onListener(){
        PluginManager pm= Bukkit.getServer().getPluginManager();

        pm.registerEvents(new EditMenu(), this);
        pm.registerEvents(new MenuListener(this), this);
    }

    public void onConfig(){
        this.tags = new Config(this, "tags");
        this.profile = new Config(this, "profile");
    }

    public Config getTagsConfig(){
        return tags;
    }

    public Config getProfileConfig(){
        return profile;
    }

    public ProfileManager getProfileManager(){
        return profileManager;
    }

    public ModuleManager getModuleManager(){
        return moduleManager;
    }

    public void onReload(){
        this.reloadConfig();
        tags.reload();
    }

    public static void async(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(Neon.getPlugin(), callable::call);
    }

    public interface Callable {
        void call();
    }
}
