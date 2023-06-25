package dev.hely.tag;

import com.google.common.collect.ImmutableList;
import dev.hely.lib.CC;
import dev.hely.lib.command.CommandManager;
import dev.hely.lib.configuration.Config;
import dev.hely.lib.manager.Manager;
import dev.hely.lib.menu.MenuListener;
import dev.hely.lib.menu.MenuManager;
import dev.hely.lib.sound.SoundManager;
import dev.hely.tag.command.TagCommand;
import dev.hely.tag.configuration.Configuration;
import dev.hely.tag.menu.edit.EditMenu;
import dev.hely.tag.module.ModuleManager;
import dev.hely.tag.profile.ProfileManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Neon extends JavaPlugin {

    @Getter private static Neon instance;
    private List<Manager> managers;
    private Config tagsConfig;
    private Config profileConfig;
    private ModuleManager moduleManager;
    private ProfileManager profileManager;

    
    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();


        this.setupManager();
        this.setupConfig();
        this.setupCommand();
        this.setupListener();

        Configuration.loadConfig();

        this.moduleManager = new ModuleManager();
        this.profileManager = new ProfileManager();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPIExpansion().register();
        }

        Bukkit.getConsoleSender().sendMessage(CC.translate("&9"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9Loading plugin..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9Neon Tags"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9Authors: " + instance.getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&9Version: " + instance.getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        this.managers.forEach(manager -> manager.onDisable(this));
        this.moduleManager.onDisable();
        this.profileManager.onDisable();
        instance = null;
    }


    public void setupCommand() {
        CommandManager command = CommandManager.INSTANCE;
        command.registerCommand(new TagCommand());
    }

    public void setupManager() {
        this.managers = new ArrayList<>();
        this.managers.add(SoundManager.INSTANCE);
        this.managers.add(MenuManager.INSTANCE);
        this.managers.add(CommandManager.INSTANCE);

        this.managers.forEach(manager -> manager.onEnable(this));
    }

    public void setupListener(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new EditMenu(), this);
        pluginManager.registerEvents(new MenuListener(this), this);
    }

    public void setupConfig(){
        this.tagsConfig = new Config(this, "tags");
        this.profileConfig = new Config(this, "profile");
    }

    public void onReload(){
        this.reloadConfig();
        tagsConfig.reload();
        Configuration.loadConfig();
    }
}
