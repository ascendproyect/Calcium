package org.ascend.calcium.command;

import dev.hely.lib.CC;
import dev.hely.lib.command.sub.SubCommand;
import dev.hely.lib.command.sub.SubCommandHandler;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.command.impl.*;
import org.ascend.calcium.configuration.Configuration;
import org.ascend.calcium.menu.TagMenu;
import org.ascend.calcium.module.category.Category;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TagSubCommand extends SubCommandHandler {
    public TagSubCommand() {
        super("tag", Arrays.asList("tags", "prefix"));

        this.addSubCommand(new TagPluginInfoCommand());
        this.addSubCommand(new TagSettingsCommand());
        this.addSubCommand(new TagCreateCommand());
        this.addSubCommand(new TagDeleteCommand());
        this.addSubCommand(new TagSaveCommand());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                SubCommand subCommand = getSubCommand(args[0]);
                if (subCommand != null) {
                    subCommand.execute(sender, args);
                } else {
                    for (Category category : Calcium.getInstance().getModuleManager().getCategory().getCategory()) {
                        if (category.getSlot() > Configuration.Menu_Raw * 9) {
                            sender.sendMessage(CC.translate("&6[Calcium] &4Critical Error! &cTheir is categories that are out of bounds in the tag menu!"));
                            return false;
                        }
                    }
                    new TagMenu().openMenu((Player) sender);
                }
            } else {
                for (Category category : Calcium.getInstance().getModuleManager().getCategory().getCategory()) {
                    if (category.getSlot() > Configuration.Menu_Raw * 9) {
                        sender.sendMessage(CC.translate("&6[Calcium] &4Critical Error! &cTheir is categories that are out of bounds in the tag menu!"));
                        return false;
                    }
                }
                new TagMenu().openMenu((Player) sender);
            }
            return false;
        }
        return false;
    }
}
