package dev.hely.tag.menu.edit;

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.module.category.Category;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditCatMenu extends Menu {

    private final Category category;

    @Override
    public String getTitle(Player player) {
        return "&7Category edit";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        button.put(0, new DisplaynameButton(category));
        button.put(1, new ItemButton(category));
        button.put(2, new SlotButton(category));
        button.put(8, new TitleButton(category));
        return button;
    }

    @AllArgsConstructor
    private static class DisplaynameButton extends Button{

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.SIGN).displayName("&eDisplayname").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            player.closeInventory();
            player.sendMessage(CC.translate("&eWrite the new displayname."));
            player.sendMessage(CC.translate("&cWrite 'cancel' to cancel the edit."));
            EditMenu.edit.put(player.getUniqueId(), "categoryname");
            EditMenu.category.put(player.getUniqueId(), category.getName());
        }
    }

    @AllArgsConstructor
    private static class ItemButton extends Button{

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(category.getItem().getType())
                    .data(category.getItem().getData().getData()).displayName("").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            player.closeInventory();
            player.sendMessage(CC.translate("&eRight click on the item"));
            EditMenu.edit.put(player.getUniqueId(), category.getName());
        }
    }
    @AllArgsConstructor
    private static class SlotButton extends Button{

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.ITEM_FRAME).displayName("&eSlot").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new SlotMenu(category).openMenu(player);
        }
    }

    @AllArgsConstructor
    private static class TitleButton extends Button{

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.SIGN).displayName("&eTitle").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            EditMenu.category.put(player.getUniqueId(), category.getName());
            EditMenu.edit.put(player.getUniqueId(), "category");
            player.closeInventory();
            player.sendMessage(CC.translate("&eWrite the new title."));
            player.sendMessage(CC.translate("&cWrite 'cancel' to cancel the edit."));
        }
    }
}
