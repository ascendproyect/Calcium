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
    public String getName() {
        return "editfill";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Fill Data Editing";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
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
        public ItemStack getItemStack(Player player) {
            if(Configuration.Menu_FillData == data){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&7").setData((short) data).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&7").setData((short) data).build();
            }
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(Configuration.Menu_FillData != data){
                FileConfiguration config = Neon.getInstance().getConfig();
                config.set("settings.menu.fill.data", data);
                Neon.getInstance().saveConfig();
                Neon.getInstance().onReload();
                new EditMenu().openMenu(player);
            }
        }
    }
}
