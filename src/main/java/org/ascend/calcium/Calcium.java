package org.ascend.calcium;

import dev.hely.lib.CC;
import dev.hely.lib.configuration.Config;
import dev.hely.lib.manager.Manager;
import dev.hely.lib.menu.MenuListener;
import dev.hely.lib.menu.MenuManager;
import dev.hely.lib.sound.SoundManager;
import lombok.Getter;
import org.ascend.calcium.configuration.Configuration;
import org.ascend.calcium.menu.edit.EditMenu;
import org.ascend.calcium.module.ModuleManager;
import org.ascend.calcium.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Calcium extends JavaPlugin {

    @Getter private static Calcium instance;
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
        this.setupListener();

        Configuration.loadConfig();

        this.moduleManager = new ModuleManager();
        this.profileManager = new ProfileManager();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPIExpansion().register();
        }

        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m=================================================================================="));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6Calcium Chat Tags Plugin"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7Attempting to load plugin.."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6- &7Authors: &f" + instance.getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6- &7Version: &f" + instance.getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m=================================================================================="));
    }

    @Override
    public void onDisable() {
        this.managers.forEach(manager -> manager.onDisable(this));
        this.moduleManager.onDisable();
        this.profileManager.onDisable();
        instance = null;
    }

    public void setupManager() {
        this.managers = new ArrayList<>();
        this.managers.add(SoundManager.INSTANCE);
        this.managers.add(MenuManager.INSTANCE);

        this.managers.forEach(manager -> manager.onEnable(this));
    }

    public void setupListener(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new EditMenu(), this);
        pluginManager.registerEvents(new MenuListener(this), this);
    }

    public void setupConfig(){
        try {
            this.tagsConfig = new Config(this, "tags");
            this.profileConfig = new Config(this, "profile");
        } catch (IOException | InvalidConfigurationException e) {
            ConsoleCommandSender bukkitCommandSender = Bukkit.getConsoleSender();

            bukkitCommandSender.sendMessage(CC.translate("&7&m=================================================================================="));
            bukkitCommandSender.sendMessage(CC.translate("&6Calcium Chat Tags - Configuration Error"));
            bukkitCommandSender.sendMessage(CC.translate("&cThis is more than likely caused by a broken configuration file!"));
            bukkitCommandSender.sendMessage(CC.translate("&cError:"));
            e.printStackTrace();
            bukkitCommandSender.sendMessage(CC.translate("&7&m=================================================================================="));
        }
    }

    public void onReload(){
        this.reloadConfig();
        tagsConfig.reload();
        Configuration.loadConfig();
    }
}
