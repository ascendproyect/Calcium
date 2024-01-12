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

import org.ascend.calcium.menu.SettingsMenu;
import org.ascend.calcium.util.command.sub.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class TagSettingsCommand extends SubCommand {
    public TagSettingsCommand() {
        super("settings", Collections.singletonList("configure"), "calcium.admin", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;

        new SettingsMenu().openMenu(player);
    }
}

