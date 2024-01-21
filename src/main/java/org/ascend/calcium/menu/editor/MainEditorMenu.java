package org.ascend.calcium.menu.editor;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.editor.plugin.PluginEditorMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MainEditorMenu extends Menu {
    {
        setPlaceholder(true);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "Plugin Editor";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        button.put(11, new EditButton());
        button.put(13, new EditCategoryButton());
        button.put(15, new EditTagButton());
        button.put(21, new ReloadButton());
        button.put(23, new ReloadButton());

        return button;
    }


    private static class EditCategoryButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.ICE)
                    .setDisplayName("&6Category Settings")
                    .setLore(
                            "&7This will allow you to edit the",
                            "&7setting's for each of the categories.",
                            "",
                            "&6Left-Click &7to view category settings."
                    )
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new SelectCategoryMenu().openMenu(player);
        }
    }

    private static class EditButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.BOOK_AND_QUILL).setDisplayName("&6Main Menu Settings")
                    .setLore(
                            "&7This will allow you to edit the",
                            "&7setting's for the main menu.",
                            "",
                            "&6Left-Click &7to edit the main menu."
                    ).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new PluginEditorMenu().openMenu(player);
        }
    }

    private static class EditTagButton extends Button {
        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.NAME_TAG).setDisplayName("&6Main Menu Settings")
                    .setLore(
                            "&7This will allow you to edit the",
                            "&7setting's for any of the chat tag.",
                            "",
                            "&6Left-Click &7to edit the chat tag's."
                    ).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new SelectTagMenu().openMenu(player);
        }
    }

    private static class ReloadButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.REDSTONE_BLOCK).setDisplayName("&c&lReload Plugin")
                    .setLore("&7This will reload your plugin along", "&7with all it's components!", "", "&cWARNING: &7This could cause brief lag!").addFakeGlow().build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            Calcium.getInstance().onReload();
        }
    }
}
