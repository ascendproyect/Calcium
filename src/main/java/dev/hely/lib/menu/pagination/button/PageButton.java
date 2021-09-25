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


public class PageButton extends Button {
    private final int mod;
    private final PaginatedMenu menu;

    public PageButton(int mod, PaginatedMenu menu) {
        this.mod = mod;
        this.menu = menu;
    }
    @Override
    public ItemStack getButtonItem(Player player) {
        ItemMaker itemMaker = ItemMaker.of(Material.GOLD_NUGGET);
        if (this.hasNext(player)) {
            itemMaker.displayName((this.mod > 0) ? "&aNext Page" : "&cPrevious Page");
        } else {
            itemMaker.displayName((this.mod > 0) ? "&aLast Page" : "&aFirst Page");
        }
        itemMaker.lore("", "&eRight click to", "&ejump to a page.", "");
        return itemMaker.build();
    }
    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        if (clickType.equals(ClickType.RIGHT)) {
            if (!this.menu.isInfoButton()) {
                new ViewAllPagesMenu(this.menu).openMenu(player);
                Button.playNeutral(player);
            }
        } else if (this.hasNext(player)) {
            this.menu.modPage(player, this.mod);
            Button.playNeutral(player);
        } else {
            Button.playFail(player);
        }
    }
    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0 && this.menu.getPages(player) >= pg;
    }
}
