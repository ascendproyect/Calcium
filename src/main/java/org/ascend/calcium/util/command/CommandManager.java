package org.ascend.calcium.util.command;

import dev.hely.lib.CC;
import lombok.Getter;
import lombok.Setter;
import org.ascend.calcium.Calcium;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class CommandManager {
    private Calcium plugin;

    private List<BaseCommand> loadedCommands;
    private List<BaseCommand> disabledCommands;

    public void onEnable() {
        this.plugin = Calcium.getInstance();
        loadedCommands = new ArrayList<>();
        disabledCommands = new ArrayList<>();
        setupCommand();
    }

    public void setupCommand() {

        loadedCommands.forEach(this::registerCommand);

        Bukkit.getConsoleSender().sendMessage(CC.translate("&aLoading " + loadedCommands.size() + " commands.."));
    }


    public void registerCommand(BukkitCommand command) {
        if (!plugin.getConfig().getStringList("settings.command-system.disabled-commands").contains(command.getName())) {
            if (this.getCommandMap() != null) {
                this.getCommandMap().register(plugin.getDescription().getName(), command);
            }
            this.disabledCommands.removeIf(baseCommand -> baseCommand.equals(command));
        } else {
            this.disabledCommands.add((BaseCommand) command);
        }
    }

    public void unregisterCommand(BaseCommand bukkitCommand) {
        this.loadedCommands.removeIf(baseCommand -> baseCommand.equals(bukkitCommand));

        if (this.getCommandMap() != null) {
            Command command = this.getCommandMap().getCommand(bukkitCommand.getName());

            if (command != null) {
                try {
                    Field field = getCommandMap().getClass().getDeclaredField("knownCommands");
                    field.setAccessible(true);

                    Map<String, Command> commands = (Map<String, Command>) field.get(getCommandMap());
                    commands.remove(command.getName().toLowerCase(Locale.ENGLISH).trim());
                    command.getAliases().forEach(alias -> {
                        commands.remove(alias.toLowerCase(Locale.ENGLISH).trim());
                    });

                    field.set(getCommandMap(), commands);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        this.disabledCommands.add(bukkitCommand);
    }

    public boolean isDisabled(BaseCommand baseCommand) {
        return this.disabledCommands.contains(baseCommand);
    }

    private CommandMap getCommandMap() {
        PluginManager manager = plugin.getServer().getPluginManager();
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(manager);
        } catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
