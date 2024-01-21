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

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.configuration.Configuration;
import org.ascend.calcium.menu.editor.plugin.PluginEditorMenu;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditMainMenuRowsButton extends Button {
    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.PAINTING)
                .setDisplayName("&6Edit Rows")
                .setLore(
                        "&7This will allow you to change the",
                        "&7rows of the main menu.",
                        "",
                        "&6- &7Current Rows: &a" + Configuration.Menu_Raw + " Rows",
                        "",
                        "&6Left-Click &7to edit this main menu rows."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();

        new Menu() {

            @Override
            public String getName() {
                return "rowSelector";
            }

            @Override
            public String getTitle(Player player) {
                return "Select Row Size";
            }

            @Override
            public Map<Integer, Button> getMenuContent(Player player) {
                Map<Integer, Button> button= new HashMap<>();
                for(int j=0;j<=5;j++){
                    button.put(j, new RowButton(j));
                }
                return button;
            }
        }.openMenu(player);
    }

    @AllArgsConstructor
    private static class RowButton extends Button {
        private final int row;

        @Override
        public ItemStack getItemStack(Player player) {
            if(Configuration.Menu_Raw == (row + 1)){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&a" + row + 1).setData((short) 5).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&cNot Available").setData((short) 15).build();
            }
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(Configuration.Menu_Raw != (row + 1)){
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("settings.menu.raw", (row + 1));
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().onReload();
                new PluginEditorMenu().openMenu(player);
            }
        }
    }

}
