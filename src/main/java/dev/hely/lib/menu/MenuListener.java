package dev.hely.lib.menu;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.button.Button;
import dev.hely.lib.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuListener implements Listener {
    private final JavaPlugin plugin;

    public MenuListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onButtonPress(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu openMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
        if (openMenu != null) {
            if (player.getOpenInventory() != null && player.getOpenInventory().getTitle().equals(CC.translate(openMenu.getTitle(player)))) {
                event.setCancelled(true);
            }
            if (!openMenu.isAllowMoveInventory()) {
                if (event.getSlot() != event.getRawSlot()) {
                    if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                        event.setCancelled(true);
                    }
                    return;
                }
                if (openMenu.getButtons().containsKey(event.getSlot())) {
                    Button button = openMenu.getButtons().get(event.getSlot());
                    boolean cancel = button.shouldCancel(player, event.getClick());
                    if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                        event.setCancelled(true);
                        if (event.getCurrentItem() != null) {
                            player.getInventory().addItem(event.getCurrentItem());
                        }
                    } else {
                        event.setCancelled(cancel);
                    }
                    button.clicked(player, event.getClick());
                    button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());
                    if (Menu.getCurrentlyOpenedMenus().containsKey(player.getName())) {
                        Menu newMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
                        if (newMenu == openMenu) {
                            boolean buttonUpdate = button.shouldUpdate(player, event.getClick());
                            if (buttonUpdate) {
                                openMenu.setClosedByMenu(true);
                                newMenu.openMenu(player);
                            }
                        }
                        if (!newMenu.isAllowPlayerInventoryClick() && event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory())) {
                            event.setCancelled(true);
                        }
                    } else if (button.shouldUpdate(player, event.getClick())) {
                        openMenu.setClosedByMenu(true);
                        openMenu.openMenu(player);
                    }
                    if (event.isCancelled()) {
                        Bukkit.getScheduler().runTaskLater(this.plugin, player::updateInventory, 1L);
                    }
                } else {
                    if (event.getCurrentItem() != null) {
                        event.setCancelled(true);
                    }
                    if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu openMenu = Menu.getCurrentlyOpenedMenus().get(player.getName());
        if (openMenu != null) {
            openMenu.onClose(player);
            Menu.getCurrentlyOpenedMenus().remove(player.getName());
        }
    }
}
