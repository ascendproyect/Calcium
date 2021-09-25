package dev.hely.lib.menu.button;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.maker.ItemMaker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public static Button placeholder(Material material, byte data, String... title) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                ItemMaker itemMaker = ItemMaker.of(material);
                itemMaker.displayName(StringUtils.join(title));
                itemMaker.data(data);
                return itemMaker.build();
            }
        };
    }

    public static void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 20.0f, 0.1f);
    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20.0f, 15.0f);
    }

    public static void playNeutral(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 20.0f, 1.0f);
    }

    public abstract ItemStack getButtonItem(Player player);

    public void clicked(Player player, ClickType clickType) {
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
    }

    public boolean shouldCancel(Player player, ClickType clickType) {
        return true;
    }

    public boolean shouldUpdate(Player player, ClickType clickType) {
        return false;
    }
}
