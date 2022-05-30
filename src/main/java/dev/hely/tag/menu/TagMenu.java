package dev.hely.tag.menu;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
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
    public String getTitle(Player player) {
        return Configuration.Menu_Title;
    }

    @Override
    public int getSize() {
        return Configuration.Menu_Raw * 9;
    }

    public void onOpen(Player player){
        try{
            player.playSound(player.getLocation(),  Sound.CHEST_OPEN, 2, 2);
        }catch (Exception e){
            Bukkit.getLogger().warning("An error occurred while trying to produce a sound");
        }
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        if(Configuration.Menu_Fill){
            for (int i = 0; i < getSize(); ++i) {
                button.put(i, new SeparatorButton());
            }
        }

        Neon.getPlugin().getModuleManager().getCategory().getCategory().forEach(category -> button.put(category.getSlot() - 1, new CategoryButton(category)));
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
            return ItemMaker.of(Material.STAINED_GLASS_PANE).data((short) Configuration.Menu_FillData).displayName("&7")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        }
    }
}
