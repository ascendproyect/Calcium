package org.ascend.calcium.command;

import org.ascend.calcium.command.impl.TagPluginInfoCommand;
import org.ascend.calcium.command.impl.TagSettingsCommand;
import org.ascend.calcium.menu.TagMenu;
import org.ascend.calcium.util.command.sub.SubCommand;
import org.ascend.calcium.util.command.sub.SubCommandHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TagSubCommand extends SubCommandHandler {
    public TagSubCommand() {
        super("tag", Arrays.asList("tags", "prefix"), null);

        this.addSubCommand(new TagPluginInfoCommand());
        this.addSubCommand(new TagSettingsCommand());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                SubCommand subCommand = getSubCommand(args[0]);
                if (subCommand != null) {
                    subCommand.execute(sender, args);
                } else {
                    new TagMenu().openMenu((Player) sender);
                }
            } else {
                new TagMenu().openMenu((Player) sender);
            }
            return false;
        }
        return false;
    }
}
