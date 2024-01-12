package org.ascend.calcium.util.command.sub;

import dev.hely.lib.CC;
import org.ascend.calcium.Calcium;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCommandHandler extends BukkitCommand {

    private final String commandName;

    private final List<String> usageMessage;
    private final List<SubCommand> subCommands;

    public SubCommandHandler(String commandName, List<String> usageMessage) {
        this(commandName, new ArrayList<>(), usageMessage);
    }

    public SubCommandHandler(String commandName, List<String> aliases, List<String> usageMessage) {
        super(commandName);

        this.commandName = commandName;
        this.setAliases(aliases);

        this.subCommands = new ArrayList<>();
        this.usageMessage = usageMessage;

        Calcium.getInstance().getModuleManager().getCommand().registerCommand(this);
    }

    public void disable() {
        this.subCommands.clear();
    }

    protected void addSubCommand(SubCommand command) {
        this.subCommands.add(command);
    }

    protected List<String> getUsageMessage(CommandSender sender) {
        return this.usageMessage;
    }

    protected SubCommand getSubCommand(String name) {
        for(SubCommand sub : this.subCommands) {
            if(sub.getName().equalsIgnoreCase(name) || sub.getAllies().contains(name.toLowerCase())) {
                return sub;
            }
        }

        return null;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0) {
            this.getUsageMessage(sender).forEach(sender::sendMessage);
            return true;
        }

        SubCommand sub = this.getSubCommand(args[0]);

        if(sub == null) {
            sender.sendMessage(CC.translate("&cSorry, you have provided a invalid command argument."));
            return true;
        }

        if(sub.isPlayerOnly && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(CC.translate("&cSorry, this is a player only command."));
            return true;
        }

        if(sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) {
            sender.sendMessage(CC.translate("&cYou do not have permission to execute this command."));
            return true;
        }

        sub.execute(sender, Arrays.copyOfRange(args, 1, args.length));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length != 1) {
            return super.tabComplete(sender, alias, args);
        }

        List<String> completions = new ArrayList<>();

        for(SubCommand subCommand : this.subCommands) {
            if(!subCommand.getName().startsWith(args[0].toLowerCase())) continue;
            if(subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) continue;

            completions.add(subCommand.getName());
        }

        return completions;
    }
}