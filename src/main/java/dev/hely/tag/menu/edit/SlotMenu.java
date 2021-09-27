package dev.hely.tag.menu.edit;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.module.category.Category;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SlotMenu extends Menu {

    private final Category category;

    public SlotMenu(Category category) {
        this.category = category;
    }

    @Override
    public String getTitle(Player player) {
        return "&7Category edit";
    }

    @Override
    public int getSize() {
        return Neon.getPlugin().getConfig().getInt("settings.menu.main.raw") * 9;
    }


    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        for(int j=0;j<getSize();j++){
            button.put(j, new SlotButton(j, category));
        }
        return button;
    }

    @AllArgsConstructor
    private static class SlotButton extends Button{
        private final int S;
        private final Category category;
        @Override
        public ItemStack getButtonItem(Player player) {
            if(Neon.getPlugin().getConfig().getInt("categorys."+category.getName()+".slot") == S){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&aselected").data((short) 5).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&cUnselected").data((short) 15).build();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if(Neon.getPlugin().getConfig().getInt("categorys."+category.getName()+".slot") != S){
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("categorys."+category.getName()+".slot", S+1);
                Neon.getPlugin().saveConfig();
                Neon.getPlugin().getModuleManager().getCategory().onLoad();
                new EditCatMenu(category).openMenu(player);
            }
        }
    }
}
