package dev.hely.lib.menu.pagination.button;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.PaginatedMenu;
import dev.hely.lib.maker.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class JumpToPageButton extends Button {
    private final int page;
    private final PaginatedMenu menu;
    private final boolean current;

    public JumpToPageButton(int page, PaginatedMenu menu, boolean current) {
        this.page = page;
        this.menu = menu;
        this.current = current;
    }
    @Override
    public ItemStack getButtonItem(Player player) {
        ItemMaker itemMaker = ItemMaker.of(this.current ? Material.ENCHANTED_BOOK : Material.BOOK);
        itemMaker.displayName("&aPage " + this.page);
        itemMaker.amount(this.page);
        if (this.current) {
            itemMaker.lore("", "&eCurrent page", "");
        }
        return itemMaker.build();
    }
    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }
    public int getPage() {
        return this.page;
    }
    public PaginatedMenu getMenu() {
        return this.menu;
    }
    public boolean isCurrent() {
        return this.current;
    }
}
