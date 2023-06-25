package dev.hely.tag.menu;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.sound.SoundManager;
import dev.hely.tag.Neon;
import dev.hely.tag.configuration.Configuration;
import dev.hely.tag.module.category.Category;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TagMenu extends Menu {

    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getTitle(Player player) {
        return Configuration.Menu_Title;
    }

    @Override
    public int getSize() {
        return Configuration.Menu_Raw * 9;
    }

    public void onOpen(Player player){
        SoundManager.INSTANCE.playSound(player, "CHEST_OPEN");
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        if(Configuration.Menu_Fill){
            for (int i = 0; i < getSize(); ++i) {
                button.put(i, new SeparatorButton());
            }
        }

        Neon.getInstance().getModuleManager().getCategory().getCategory().forEach(category -> button.put(category.getSlot() - 1, new CategoryButton(category)));
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
            new SubTagMenu(category).openMenu(player);
        }
    }

    private static class SeparatorButton extends Button {

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.STAINED_GLASS_PANE).setData((short) Configuration.Menu_FillData).setDisplayName("&7")
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
        }
    }
}
