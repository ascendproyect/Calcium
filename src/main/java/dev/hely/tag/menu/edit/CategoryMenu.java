package dev.hely.tag.menu.edit;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.module.category.Category;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CategoryMenu extends Menu {

    private int s;

    @Override
    public String getTitle(Player player) {
        return "&9Category edit";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        s=0;
        Neon.getPlugin().getModuleManager().getCategory().getCategory().forEach(category -> {
            button.put(s, new CategoryButton(category));
            s++;
        });
        return button;
    }

    @AllArgsConstructor
    private static class CategoryButton extends Button {

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(category.getItem().getType()).data(category.getItem().getData().getData())
                    .amount(category.getItem().getAmount()).displayName(category.getDisplayname()).lore(category.getLore())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
             new EditCatMenu(category).openMenu(player);
        }
    }
}
