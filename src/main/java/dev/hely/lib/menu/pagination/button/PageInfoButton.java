package dev.hely.lib.menu.pagination.button;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.PaginatedMenu;
import dev.hely.lib.menu.pagination.ViewAllPagesMenu;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.tag.Neon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class PageInfoButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return ItemMaker.of(Material.BOOK).displayName("&9Player Info")
                .lore("",
                "&fCurrent tag&7: " + Neon.getPlugin().getProfileManager().getStorage().getTag(player.getUniqueId()) + "&e " + player.getName(),
                "", "&7(Left-Click) &cHere to remove tag", "").build();
    }
    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            Neon.getPlugin().getProfileManager().getStorage().setTag(player.getUniqueId(), "");
            player.closeInventory();
        }
    }
}
