package dev.hely.lib.menu.pagination.button;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.FilterablePaginatedMenu;
import dev.hely.lib.menu.pagination.PageFilter;
import dev.hely.lib.maker.ItemMaker;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class PageFilterButton<T> extends Button {
    private final FilterablePaginatedMenu<T> menu;

    public PageFilterButton(FilterablePaginatedMenu<T> menu) {
        this.menu = menu;
    }
    @Override
    public ItemStack getButtonItem(Player player) {
        if (this.menu.getFilters() == null || this.menu.getFilters().isEmpty()) {
            return new ItemStack(Material.AIR);
        }
        List<String> lore = new ArrayList<>();
        lore.add("");
        for (PageFilter filter : this.menu.getFilters()) {
            String decoration = "";
            String color;
            String icon;
            if (filter.isEnabled()) {
                color = ChatColor.GREEN.toString();
                icon = StringEscapeUtils.unescapeJava("\u2713");
            } else {
                color = ChatColor.RED.toString();
                icon = StringEscapeUtils.unescapeJava("\u2717");
            }
            if (this.menu.getFilters().get(this.menu.getScrollIndex()).equals(filter)) {
                decoration = ChatColor.YELLOW + StringEscapeUtils.unescapeJava("» ") + " ";
            }
            lore.add(decoration + color + icon + " " + filter.getName());
        }
        lore.add("");
        lore.add("&eLeft click to scroll.");
        lore.add("&eRight click to toggle a filter.");
        lore.add("");
        return ItemMaker.of(Material.HOPPER).displayName("&7Filters").lore(lore).build();
    }
    @Override
    public void clicked(Player player, ClickType clickType) {
        if (this.menu.getFilters() == null || this.menu.getFilters().isEmpty()) {
            player.sendMessage(ChatColor.RED + "There are no filters.");
        } else if (clickType == ClickType.LEFT) {
            if (this.menu.getScrollIndex() == this.menu.getFilters().size() - 1) {
                this.menu.setScrollIndex(0);
            } else {
                this.menu.setScrollIndex(this.menu.getScrollIndex() + 1);
            }
        } else if (clickType == ClickType.RIGHT) {
            PageFilter<T> filter = this.menu.getFilters().get(this.menu.getScrollIndex());
            filter.setEnabled(!filter.isEnabled());
        }
    }
    @Override
    public boolean shouldUpdate(Player player, ClickType clickType) {
        return true;
    }
}
