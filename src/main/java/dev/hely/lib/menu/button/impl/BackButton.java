package dev.hely.lib.menu.button.impl;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.maker.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BackButton extends Button {
    private final Menu back;
    private final ItemStack backItem;

    public BackButton(Menu back) {
        this.back = back;
        this.backItem = new ItemStack(Material.ARROW);
    }

    public BackButton(Menu back, ItemStack backItem) {
        this.back = back;
        this.backItem = backItem;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return ItemMaker.copyOf(this.backItem).displayName("&c&lBack").lore("", "&7Click here to return to", "&7the previous menu.", "").build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
