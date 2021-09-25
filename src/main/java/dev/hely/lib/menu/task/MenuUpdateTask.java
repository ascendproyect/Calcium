package dev.hely.lib.menu.task;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ConcurrentModificationException;

public class MenuUpdateTask extends BukkitRunnable {
    public void run() {
        try {
            Menu.getCurrentlyOpenedMenus().forEach((key, value) -> {
                Player player = Bukkit.getPlayer(key);
                if (player != null && value.isAutoUpdate()) {
                    value.openMenu(player);
                }
            });
        } catch (ConcurrentModificationException ex) {
        }
    }
}
