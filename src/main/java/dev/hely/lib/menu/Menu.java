package dev.hely.lib.menu;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import com.google.common.collect.Maps;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public abstract class Menu {
    private static Map<String, Menu> currentlyOpenedMenus = Maps.newHashMap();
    private final boolean autoUpdate = false;
    private final boolean placeholder = false;
    private final Button placeholderButton = Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 15, " ");
    private Map<Integer, Button> buttons = Maps.newHashMap();
    private boolean updateAfterClick = true;
    private boolean closedByMenu = false;
    private boolean allowPlayerInventoryClick = false;
    private boolean allowMoveInventory = false;
    public static Map<String, Menu> getCurrentlyOpenedMenus() {
        return Menu.currentlyOpenedMenus;
    }
    public static void setCurrentlyOpenedMenus(Map<String, Menu> currentlyOpenedMenus) {
        Menu.currentlyOpenedMenus = currentlyOpenedMenus;
    }
    private ItemStack createItemStack(Player player, Button button) {
        ItemStack item = button.getButtonItem(player);
        if (item.getType() != Material.SKULL_ITEM) {
            final ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                meta.setDisplayName(meta.getDisplayName());
            }
            item.setItemMeta(meta);
        }
        return item;
    }
    public void openMenu(Player player) {
        this.openMenu(player, false);
    }
    public void openMenu(Player player, boolean allowPlayerInventoryClick) {
        this.buttons = this.getButtons(player);
        Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getName());
        Inventory inventory = null;
        int size = (this.getSize() == -1) ? this.size(this.buttons) : this.getSize();
        boolean update = false;
        String title = CC.translate(this.getTitle(player));
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }
        if (player.getOpenInventory() != null) {
            if (previousMenu == null) {
                player.closeInventory();
            } else {
                int previousSize = player.getOpenInventory().getTopInventory().getSize();
                if (previousSize == size && player.getOpenInventory().getTopInventory().getTitle().equals(CC.translate(title))) {
                    inventory = player.getOpenInventory().getTopInventory();
                    update = true;
                } else {
                    previousMenu.setClosedByMenu(true);
                    player.closeInventory();
                }
            }
        }
        if (inventory == null) {
            inventory = Bukkit.createInventory(player, size, CC.translate(title));
        }
        inventory.setContents(new ItemStack[inventory.getSize()]);
        Menu.currentlyOpenedMenus.put(player.getName(), this);
        Menu.currentlyOpenedMenus.get(player.getName()).setAllowPlayerInventoryClick(allowPlayerInventoryClick);
        for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
            inventory.setItem(buttonEntry.getKey(), this.createItemStack(player, buttonEntry.getValue()));
        }
        if (this.isPlaceholder()) {
            for (int index = 0; index < size; ++index) {
                if (this.buttons.get(index) == null) {
                    this.buttons.put(index, this.placeholderButton);
                    inventory.setItem(index, this.placeholderButton.getButtonItem(player));
                }
            }
        }
        if (update) {
            player.updateInventory();
        } else {
            player.openInventory(inventory);
        }
        this.onOpen(player);
        this.setClosedByMenu(false);
    }
    public int size(Map<Integer, Button> buttons) {
        int highest = 0;
        for (int buttonValue : buttons.keySet()) {
            if (buttonValue > highest) {
                highest = buttonValue;
            }
        }
        return (int) (Math.ceil((highest + 1) / 9.0) * 9.0);
    }
    public int getSize() {
        return -1;
    }
    public int getSlot(int x, int y) {
        return 9 * y + x;
    }
    public abstract String getTitle(Player player);
    public abstract Map<Integer, Button> getButtons(Player player);
    public void onOpen(Player player) {
    }
    public void onClose(Player player) {
    }
    public Map<Integer, Button> getButtons() {
        return this.buttons;
    }
    public void setButtons(Map<Integer, Button> buttons) {
        this.buttons = buttons;
    }
    public boolean isAutoUpdate() {
        return this.autoUpdate;
    }
    public boolean isUpdateAfterClick() {
        return this.updateAfterClick;
    }
    public void setUpdateAfterClick(boolean updateAfterClick) {
        this.updateAfterClick = updateAfterClick;
    }
    public boolean isClosedByMenu() {
        return this.closedByMenu;
    }
    public void setClosedByMenu(boolean closedByMenu) {
        this.closedByMenu = closedByMenu;
    }
    public boolean isPlaceholder() {
        return this.placeholder;
    }
    public boolean isAllowPlayerInventoryClick() {
        return this.allowPlayerInventoryClick;
    }
    public void setAllowPlayerInventoryClick(boolean allowPlayerInventoryClick) {
        this.allowPlayerInventoryClick = allowPlayerInventoryClick;
    }
    public Button getPlaceholderButton() {
        return this.placeholderButton;
    }
    public boolean isAllowMoveInventory() {
        return allowMoveInventory;
    }
    public void setAllowMoveInventory(boolean allowMoveInventory) {
        this.allowMoveInventory = allowMoveInventory;
    }
}
