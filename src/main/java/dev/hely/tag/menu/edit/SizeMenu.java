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

public class SizeMenu extends Menu {

    @Override
    public String getName() {
        return "editsize";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Size Editing";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        for(int j=0;j<=5;j++){
            button.put(j, new RawButton(j));
        }
        return button;
    }

    @AllArgsConstructor
    private static class RawButton extends Button {

        private final int raw;

        @Override
        public ItemStack getItemStack(Player player) {
            if(Configuration.Menu_Raw == (raw + 1)){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&a" + raw + 1).setData((short) 5).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&cNot Available").setData((short) 15).build();
            }
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(Configuration.Menu_Raw != (raw + 1)){
                FileConfiguration config = Neon.getInstance().getConfig();
                config.set("settings.menu.raw", (raw + 1));
                Neon.getInstance().saveConfig();
                Neon.getInstance().onReload();
                new EditMenu().openMenu(player);
            }
        }
    }
}
