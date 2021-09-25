package dev.hely.lib.menu.pagination.button;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.PaginatedMenu;
import dev.hely.lib.menu.pagination.ViewAllPagesMenu;
import dev.hely.lib.maker.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class PageInfoButton extends Button {
    private final PaginatedMenu menu;

    public PageInfoButton(PaginatedMenu menu) {
        this.menu = menu;
    }
    @Override
    public ItemStack getButtonItem(Player player) {
        int pages = this.menu.getPages(player);
        return ItemMaker.of(Material.BOOK).displayName("&6Page Info").lore("",
                "&eYou are viewing page &f#" + this.menu.getPage() + "&e.", "&e" +
                        ((pages == 1) ? "There is &f1 &epage." : ("There are &f" + pages + " &epages.")),
                "", "&eLeft click here to", "&eview all pages.", "").build();
    }
    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            new ViewAllPagesMenu(this.menu).openMenu(player);
            Button.playNeutral(player);
        }
    }
}
