package org.ascend.calcium.menu.editor.category;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.category
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
import org.ascend.calcium.menu.editor.SelectCategoryMenu;
import org.ascend.calcium.menu.editor.category.buttons.*;
import org.ascend.calcium.module.category.Category;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditCategoryMenu extends Menu {
    Category category;
    {
        setPlaceholder(true);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "Editing Category: " + category.getName();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        button.put(0, new BackButton(new SelectCategoryMenu()));

        button.put(10, new EditCategoryNameButton(category));
        button.put(11, new EditCategoryDisplayNameButton(category));
        button.put(12, new EditCategoryLoreButton(category));
        button.put(13, new EditCategoryMaterialButton(category));
        button.put(14, new EditCategoryTitleButton(category));
        button.put(15, new EditCategorySlotButton(category));
        button.put(20, new EditCategoryPermissionButton(category));
        button.put(21, new EditCategoryBypassPermissionButton(category));
        button.put(22, new EditCategoryGlobalButton(category));
        button.put(23, new EditCategoryDefaultButton(category));

        return button;
    }
}