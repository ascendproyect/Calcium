package dev.hely.tag.menu.edit;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
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
    public String getTitle(Player player) {
        return "&7Edit menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        for(int j=0;j<=5;j++){
            button.put(j, new RawButton(j));
        }
        return button;
    }

    @AllArgsConstructor
    private static class RawButton extends Button{
        private final int raw;
        @Override
        public ItemStack getButtonItem(Player player) {
            if(Neon.getPlugin().getConfig().getInt("settings.menu.main.raw") == (raw + 1)){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&aselected").data((short) 5).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).displayName("&cUnselected").data((short) 15).build();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if(Neon.getPlugin().getConfig().getInt("settings.menu.main.raw") != (raw + 1)){
                new EditMenu().openMenu(player);
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("settings.menu.main.raw", (raw + 1));
                Neon.getPlugin().saveConfig();
            }
        }
    }
}
