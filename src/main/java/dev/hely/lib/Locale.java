package dev.hely.lib;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

import static org.bukkit.ChatColor.RED;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Wednesday, July 21, 2021
 */

@AllArgsConstructor
public enum Locale {

    NOT_FOUND(RED + "{0} with ''{1}'' was not found."), // Ability 'Snowball' not found.
    NOT_VALID(RED + "''{0}'' is not a valid {1}."); // 'Snowball' is not a valid Ability.

    private final String source;

    public String getAsString(Object... array) {
        return MessageFormat.format(source, array);
    }
}
