package dev.hely.lib.command;

import dev.hely.lib.manager.Manager;
import dev.hely.lib.wrapper.ClassWrapper;
import dev.hely.tag.command.TagCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By LeandroSSJ
 * Created on 28/08/2021
 */
public enum CommandManager implements Manager {

    INSTANCE;

    private CommandMap commandMap;
    private List<BaseCommand> commands;
    private String pluginName;

    @Override
    public void onEnable(JavaPlugin plugin) {
        commandMap = getCommandMap();
        commands = new ArrayList<>();
        pluginName = plugin.getDescription().getName();
        this.commands.add(new TagCommand());

        this.commands.forEach(this::registerCommand);
        Bukkit.getLogger().info("[Neon] Register " + this.commands.size() + " command");
    }


    private CommandMap getCommandMap() {
        return (CommandMap) new ClassWrapper(Bukkit.getServer()).getField("commandMap").get();
    }

    @Override
    public void onDisable(JavaPlugin plugin) {
        this.commands.clear();
    }


    public void registerCommand(BukkitCommand command) {
        this.commandMap.register(pluginName, command);
    }

}
