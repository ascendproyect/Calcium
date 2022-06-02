package dev.hely.lib;

import org.bukkit.ChatColor;

import static dev.hely.lib.Assert.assertNotNull;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * viernes, abril 02, 2021
 */

public class CC {
    public static String translate(String input) {
        assertNotNull(input);
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
