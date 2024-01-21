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
import dev.hely.lib.command.sub.SubCommand;
import org.ascend.calcium.Calcium;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class TagCreateCommand extends SubCommand {
    public TagCreateCommand() {
        super("create", Collections.singletonList("new"), "calcium.admin", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CC.translate("&cCorrect Usage: /tag create <name>"));
            return;
        }
        if (Calcium.getInstance().getModuleManager().getTags().getTag(args[1]) != null) {
            sender.sendMessage(CC.translate("&cSorry, their is already a chat tag with the name of '&c&l" + args[1] + "&c'."));
            return;
        }

        System.out.print("starting 11");
        Calcium.getInstance().getModuleManager().getTags().createTag(args[1]);
        System.out.print("starting 12");
        sender.sendMessage(CC.translate("&aYou have successfully created a chat tag with the name of &6" + args[1] + "&a!"));
    }
}

