package dev.hely.tag.menu;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.module.category.Category;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TagMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return Neon.getPlugin().getConfig().getString("settings.menu.main.title");
    }

    @Override
    public int getSize() {
        return Neon.getPlugin().getConfig().getInt("settings.menu.main.raw") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        if(Neon.getPlugin().getConfig().getBoolean("settings.menu.main.fill.enabled")){
            for (int i = 0; i < getSize(); ++i) {
                button.put(i, new SeparatorButton());
            }
        }

        Neon.getPlugin().getModuleManager().getCategory().getCategory().forEach(category -> {
            button.put(category.getSlot() - 1, new CategoryButton(category));
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
            new SubTagMenu(category).openMenu(player);
        }
    }

    private static class SeparatorButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.STAINED_GLASS_PANE).data((short) Neon.getPlugin().getConfig().getInt("settings.menu.main.fill.data")).displayName("&7")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        }
    }
}
