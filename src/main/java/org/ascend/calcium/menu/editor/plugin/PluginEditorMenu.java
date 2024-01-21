package org.ascend.calcium.menu.editor.plugin;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
// Ascend LLC Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor
//   Date: Saturday, January 20, 2024 - 6:34 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//   Ascend LLC Discord: https://discord.gg/xM8bktgC6q
//============================================================

import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import org.ascend.calcium.menu.editor.plugin.buttons.EditMainMenuFillButton;
import org.ascend.calcium.menu.editor.plugin.buttons.EditMainMenuFillColorButton;
import org.ascend.calcium.menu.editor.plugin.buttons.EditMainMenuRowsButton;
import org.ascend.calcium.menu.editor.plugin.buttons.EditMainMenuTitleButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PluginEditorMenu extends Menu {
    {
        setPlaceholder(true);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "Plugin Settings";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();

        button.put(10, new EditMainMenuTitleButton());
        button.put(12, new EditMainMenuRowsButton());
        button.put(14, new EditMainMenuFillButton());
        button.put(16, new EditMainMenuFillColorButton());

        return button;
    }
}
