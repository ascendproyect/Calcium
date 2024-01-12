package org.ascend.calcium.util.command;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.core.bukkit.utils.command
//   Date: Saturday, August 05, 2023 - 3:52 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import org.ascend.calcium.command.TagSubCommand;

public class SubCommandManager {

    private TagSubCommand tagSubCommand;

    public void onEnable() {
        this.tagSubCommand = new TagSubCommand();
    }

    public void onDisable() {
        this.tagSubCommand.disable();
    }
}
