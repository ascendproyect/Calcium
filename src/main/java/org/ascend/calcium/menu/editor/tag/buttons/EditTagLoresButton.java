package org.ascend.calcium.menu.editor.tag.buttons;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.tag.buttons
//   Date: Saturday, January 20, 2024 - 4:03 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import lombok.AllArgsConstructor;
import org.ascend.calcium.menu.editor.tag.EditTagLoresMenu;
import org.ascend.calcium.module.tag.Tags;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class EditTagLoresButton extends Button {
    Tags tag;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.GLOWSTONE)
                .setDisplayName("&6Edit Lores")
                .setLore(
                        "&7This will allow you to change the",
                        "&7way lores appear on this tag.",
                        "",
                        "&6Left-Click &7to edit this tag's lores."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        new EditTagLoresMenu(tag).openMenu(player);
    }
}
