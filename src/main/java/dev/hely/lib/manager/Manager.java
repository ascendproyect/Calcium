package dev.hely.lib.manager;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Thursday, March 25, 2021
 */

public interface Manager {

    default void onEnable(JavaPlugin plugin) {
    }

    default void onDisable(JavaPlugin plugin) {
    }
}
