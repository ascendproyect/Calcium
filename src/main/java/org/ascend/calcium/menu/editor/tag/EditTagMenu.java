package org.ascend.calcium.menu.editor.tag;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.tag
//   Date: Friday, January 12, 2024 - 4:55 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.button.implement.BackButton;
import lombok.AllArgsConstructor;
import org.ascend.calcium.menu.editor.SelectTagMenu;
import org.ascend.calcium.menu.editor.tag.buttons.*;
import org.ascend.calcium.module.tag.Tags;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditTagMenu extends Menu {
    Tags tag;
    {
        setPlaceholder(true);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "Editing Tag: " + tag.getName();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        button.put(0, new BackButton(new SelectTagMenu()));

        button.put(11, new EditTagNameButton(tag));
        button.put(12, new EditTagPrefixButton(tag));
        button.put(13, new EditTagPermissionButton(tag));
        button.put(14, new EditTagMaterialButton(tag));
        button.put(15, new EditTagCategoryButton(tag));
        button.put(22, new EditTagLoresButton(tag));

        return button;
    }
}
