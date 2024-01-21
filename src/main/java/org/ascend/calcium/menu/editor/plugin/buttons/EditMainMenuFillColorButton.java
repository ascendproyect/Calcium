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

import com.cryptomorin.xseries.XMaterial;
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
public class EditMainMenuFillColorButton extends Button {
    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(XMaterial.matchXMaterial(Material.STAINED_GLASS_PANE).parseMaterial())
                .setDisplayName("&6Fill Color")
                .setData((short) Configuration.Menu_FillData)
                .setLore(
                        "&7This will change the fill color",
                        "&7of the main menu.",
                        "",
                        "&6- &7Current Color ID: &a" + Configuration.Menu_FillData,
                        "",
                        "&6Left-Click &7to update fill color."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        new Menu() {

            @Override
            public String getName() {
                return "fillColorMenu";
            }

            @Override
            public String getTitle(Player player) {
                return "Edit Fill Color";
            }

            @Override
            public Map<Integer, Button> getMenuContent(Player player) {
                Map<Integer, Button> button= new HashMap<>();
                for(int j=0;j<=15;j++){
                    button.put(j, new ColorButton(j));
                }
                return button;

            }
        }.openMenu(player);
    }

    @AllArgsConstructor
    private static class ColorButton extends Button{
        private final int data;

        @Override
        public ItemStack getItemStack(Player player) {
            if (Configuration.Menu_FillData == data) {
                return ItemMaker.of(XMaterial.matchXMaterial(Material.STAINED_GLASS_PANE).parseMaterial()).setDisplayName("&7").setData((short) data).build();
            } else {
                return ItemMaker.of(XMaterial.matchXMaterial(Material.STAINED_GLASS_PANE).parseMaterial()).setDisplayName("&7").setData((short) data).build();
            }
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if (Configuration.Menu_FillData != data) {
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("settings.menu.fill.data", data);
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().onReload();
                new PluginEditorMenu().openMenu(player);
            }
        }
    }

    @Override
    public boolean toUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }
}
