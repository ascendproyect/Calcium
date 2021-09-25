package dev.hely.tag;

import com.google.common.collect.ImmutableList;
import dev.hely.lib.command.CommandManager;
import dev.hely.lib.configuration.Config;
import dev.hely.lib.manager.Manager;
import dev.hely.lib.menu.MenuListener;
import dev.hely.tag.module.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Neon extends JavaPlugin {

    private static Neon plugin;
    private List<Manager> managerList;
    private Config tags;
    private ModuleManager moduleManager;

    @Override
    public void onLoad() {
        managerList = ImmutableList.of(
                CommandManager.INSTANCE);
    }
    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        this.onConfig();
        this.onListener();
        managerList.forEach(manager -> manager.onEnable(this));
        this.moduleManager = new ModuleManager();
    }

    @Override
    public void onDisable() {
        managerList.forEach(manager -> manager.onDisable(this));
        this.moduleManager.onDisable();
        plugin = null;
    }

    public static Neon getPlugin(){
        return plugin;
    }

    public void onListener(){
        PluginManager pm= Bukkit.getServer().getPluginManager();

        pm.registerEvents(new MenuListener(this), this);
    }

    public void onConfig(){
        this.tags = new Config(this, "tags.yml");
    }

    public Config getTagsConfig(){
        return tags;
    }

    public ModuleManager getModuleManager(){
        return moduleManager;
    }

    public void onReload(){
        this.reloadConfig();
        tags.reload();
    }
}
