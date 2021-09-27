package dev.hely.tag.command;

import dev.hely.lib.CC;
import dev.hely.lib.command.BaseCommand;
import dev.hely.tag.Neon;
import dev.hely.tag.menu.SettingsMenu;
import dev.hely.tag.menu.TagMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TagCommand extends BaseCommand {
    public TagCommand() {
        super("tag", Arrays.asList("tags", "prefix"), "neon.command.tag");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;

        Player player = (Player) sender;
        if(args.length == 0){
            new TagMenu().openMenu(player);
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("settings")){
                new SettingsMenu().openMenu(player);
            }else if(args[0].equalsIgnoreCase("info")){
                player.sendMessage(CC.translate("&7"));
                player.sendMessage(CC.translate("  &9&lNeon tags"));
                player.sendMessage(CC.translate("&7"));
                player.sendMessage(CC.translate("&7 Version: " + Neon.getPlugin().getDescription().getVersion()));
                player.sendMessage(CC.translate("&7 Author: ByJoako and LeandroSSJ"));
                player.sendMessage(CC.translate("&7"));
            }
        }
    }
}
