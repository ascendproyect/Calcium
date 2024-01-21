package org.ascend.calcium.menu.editor.category.buttons;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.category.buttons
//   Date: Saturday, January 20, 2024 - 5:56 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.module.category.Category;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class EditCategoryGlobalButton extends Button {
    Category category;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(getMaterial())
                .setDisplayName("&6Global Category")
                .setLore(
                        "&7This will allow make it so all",
                        "&7tags appear in this menu.",
                        "",
                        "&6- &7Current Status: &a" + (this.category.isGlobal() ? "&cFalse" : "&aTrue"),
                        "",
                        "&6Left-Click &7to toggle the global category."
                )
                .build();
    }

    public Material getMaterial() {
        if (this.category.isGlobal()) {
            return Material.REDSTONE_BLOCK;
        } else {
            return Material.EMERALD_BLOCK;
        }
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        category.setGlobal(!this.category.isGlobal());
        Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
        player.sendMessage(CC.translate("&6[Calcium] &aCategory Global status has been updated to &f" + (this.category.isGlobal() ? "&cFalse" : "&aTrue") + "&a."));
    }

    @Override
    public boolean toUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }
}
