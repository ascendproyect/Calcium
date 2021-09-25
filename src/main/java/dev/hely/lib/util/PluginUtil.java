package dev.hely.lib.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Wednesday, July 21, 2021
 */

@UtilityClass
public class PluginUtil {

    public static boolean isEnabled(String name) {
        return Bukkit.getServer().getPluginManager().isPluginEnabled(name);
    }
}
