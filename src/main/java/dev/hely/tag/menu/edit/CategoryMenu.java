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
    public String getName() {
        return "category";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Category Edit";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        s=0;
        Neon.getInstance().getModuleManager().getCategory().getCategory().forEach(category -> {
            button.put(s, new CategoryButton(category));
            s++;
        });
        return button;
    }

    @AllArgsConstructor
    private static class CategoryButton extends Button {

        private final Category category;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(category.getItem().getType()).setData(category.getItem().getData().getData())
                    .setAmount(category.getItem().getAmount()).setDisplayName(category.getDisplayname()).setLore(category.getLore())
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
             new EditCatMenu(category).openMenu(player);
        }
    }
}
