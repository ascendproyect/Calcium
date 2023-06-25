package dev.hely.tag.menu;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.menu.edit.CategoryMenu;
import dev.hely.tag.menu.edit.EditMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SettingsMenu extends Menu {

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Tags Edit";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        button.put(0, new EditButton());
        button.put(1, new EditCategoryButton());
        button.put(8, new ReloadButton());

        return button;
    }


    private static class EditCategoryButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.BOOK_AND_QUILL).setDisplayName("&6Category Settings")
                    .setLore("", "&eClick to edit the category", "").build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new CategoryMenu().openMenu(player);
        }
    }

    private static class EditButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.BOOK_AND_QUILL).setDisplayName("&6Menu Settings")
                    .setLore("", "&eClick to edit menu", "").build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new EditMenu().openMenu(player);
        }
    }

    private static class ReloadButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.REDSTONE_TORCH_ON).setDisplayName("&6Reload")
                    .setLore("", "&eClick to reload plugin", "").build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            Neon.getInstance().onReload();
        }
    }
}
