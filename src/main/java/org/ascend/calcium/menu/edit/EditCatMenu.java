package org.ascend.calcium.menu.edit;

import org.ascend.calcium.module.category.Category;
import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
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
    public String getName() {
        return "editcategory";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Category Edit";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
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
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.SIGN).setDisplayName("&6&lEdit Display Name")
                    .setLore("&7Click here to update this category", "&7display name!", "", "&6Current Display Name: " + category.getDisplayname()).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
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
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(category.getItem().getType())
                    .setData(category.getItem().getData().getData()).setDisplayName("&6&lEdit Category Material")
                    .setLore("&7Click here to update this category",
                            "&7display material and data!",
                            "",
                            "&6Current Material: &f" + category.getItem().getType().toString(),
                            "&6Current Data: &f" + category.getItem().getData().getData(),
                            "",
                            "&7Click to update this category item.").build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            player.closeInventory();
            player.sendMessage(CC.translate("&eRight click with the item to select. To cancel this action click with an empty hand."));
            EditMenu.edit.put(player.getUniqueId(), category.getName());
        }
    }
    @AllArgsConstructor
    private static class SlotButton extends Button{

        private final Category category;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.PAINTING).setDisplayName("&6&lSlot Editor")
                    .setLore("&7Click here to update the slot of the category!",
                            "",
                            "&6Current Slot: &f" + category.getSlot()).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new SlotMenu(category).openMenu(player);
        }
    }

    @AllArgsConstructor
    private static class TitleButton extends Button{

        private final Category category;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.SIGN).setDisplayName("&6&lEdit Menu Title")
                    .setLore("&7Click here to update this category",
                            "&7title menu!",
                            "",
                            "&6Current Menu Title: &f" + category.getTitle()).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            EditMenu.category.put(player.getUniqueId(), category.getName());
            EditMenu.edit.put(player.getUniqueId(), "category");
            player.closeInventory();
            player.sendMessage(CC.translate("&eWrite the new title."));
            player.sendMessage(CC.translate("&cWrite 'cancel' to cancel the edit."));
        }
    }
}
