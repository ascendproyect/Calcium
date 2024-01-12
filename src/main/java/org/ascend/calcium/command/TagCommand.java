package org.ascend.calcium.command;

import dev.hely.lib.CC;
import dev.hely.lib.command.BaseCommand;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.SettingsMenu;
import org.ascend.calcium.menu.TagMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TagCommand extends BaseCommand {
    public TagCommand() {
        super("tag", Arrays.asList("tags", "prefix"), "calcium.tags");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;

        Player player = (Player) sender;
        if(args.length == 0){
            new TagMenu().openMenu(player);
        }else if(args.length == 1){
            if (args[0].equalsIgnoreCase("settings") && player.hasPermission("calcium.tag.manager")){
                new SettingsMenu().openMenu(player);
            } else if(args[0].equalsIgnoreCase("info")){
                player.sendMessage(CC.translate("&7&l&m-------------------------------------"));
                player.sendMessage(CC.translate("&6&lCalcium Chat Tags"));
                player.sendMessage(CC.translate("&7This is a free, and open source plugin!"));
                player.sendMessage(CC.translate("&7"));
                player.sendMessage(CC.translate("&6- Version&7: &f" + Calcium.getInstance().getDescription().getVersion()));
                player.sendMessage(CC.translate("&6- Author&7: &fLeandroSSJ & DevDipin"));
                player.sendMessage(CC.translate("&7&l&m-------------------------------------"));
            }
        }
    }
}
