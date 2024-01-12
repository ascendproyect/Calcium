package org.ascend.calcium.command.impl;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.core.ranks.rank.commands.subcommands
//   Date: Wednesday, July 05, 2023 - 3:02 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.CC;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.util.command.sub.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class TagPluginInfoCommand extends SubCommand {
    public TagPluginInfoCommand() {
        super("plugininfo", Collections.singletonList("version"), "calcium.admin", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;

        player.sendMessage(CC.translate("&7&l&m-------------------------------------"));
        player.sendMessage(CC.translate("&6&lCalcium Chat Tags"));
        player.sendMessage(CC.translate("&7This is a free, and open source plugin!"));
        player.sendMessage(CC.translate("&7"));
        player.sendMessage(CC.translate("&6- Version&7: &f" + Calcium.getInstance().getDescription().getVersion()));
        player.sendMessage(CC.translate("&6- Author&7: &fLeandroSSJ & DevDipin"));
        player.sendMessage(CC.translate("&7&l&m-------------------------------------"));
    }
}

