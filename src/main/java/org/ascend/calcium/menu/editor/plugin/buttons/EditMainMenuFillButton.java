package org.ascend.calcium.menu.editor.plugin.buttons;

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
import org.ascend.calcium.configuration.Configuration;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class EditMainMenuFillButton extends Button {
    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(getMaterial())
                .setDisplayName("&6Fill Menu")
                .setLore(
                        "&7This will fill the main menu",
                        "&7with placeholders.",
                        "",
                        "&6- &7Current Status: &a" + (Configuration.Menu_Fill ? "&cFalse" : "&aTrue"),
                        "",
                        "&6Left-Click &7to toggle the fill menu."
                )
                .build();
    }

    public Material getMaterial() {
        if (Configuration.Menu_Fill) {
            return Material.REDSTONE_BLOCK;
        } else {
            return Material.EMERALD_BLOCK;
        }
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        FileConfiguration config = Calcium.getInstance().getConfig();
        if (Configuration.Menu_Fill) {
            config.set("settings.menu.fill.enabled", false);
        } else {
            config.set("settings.menu.fill.enabled", true);
        }
        Calcium.getInstance().saveConfig();
        Calcium.getInstance().onReload();
        player.sendMessage(CC.translate("&6[Calcium] &aMain menu fill status has been updated to &f" + (Configuration.Menu_Fill ? "&cFalse" : "&aTrue") + "&a."));
    }

    @Override
    public boolean toUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }
}
