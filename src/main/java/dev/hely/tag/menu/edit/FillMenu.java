package dev.hely.tag.menu.edit;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.configuration.Configuration;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FillMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return "&7Edit menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        for(int j=0;j<=15;j++){
            button.put(j, new FillButton(j));
        }
        return button;
    }

    @AllArgsConstructor
    private static class FillButton extends Button{

        private final int data;
        @Override
        public ItemStack getButtonItem(Player player) {
            if(Configuration.Menu_FillData == data){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&aselected").data((short) data).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&cUnselected").data((short) data).build();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if(Configuration.Menu_FillData != data){
                new EditMenu().openMenu(player);
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("settings.menu.fill.data", data);
                Neon.getPlugin().saveConfig();
            }
        }
    }
}
