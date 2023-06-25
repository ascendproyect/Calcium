package dev.hely.tag.menu.edit;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.configuration.Configuration;
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
    public String getName() {
        return "editslot";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Slot Editing";
    }

    @Override
    public int getSize() {
        return Configuration.Menu_Raw * 9;
    }


    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
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
        public ItemStack getItemStack(Player player) {
            if(Neon.getInstance().getConfig().getInt("categorys."+category.getName()+".slot") - 1 == S){
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&&cNot Available").setData((short) 14).build();
            }else{
                return ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName("&aAvailable").setData((short) 15).build();
            }
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(Neon.getInstance().getConfig().getInt("categorys."+category.getName()+".slot") - 1 != S){
                FileConfiguration config = Neon.getInstance().getConfig();
                config.set("categorys."+category.getName()+".slot", S+1);
                Neon.getInstance().saveConfig();
                Neon.getInstance().getModuleManager().getCategory().onLoad();
                new EditCatMenu(category).openMenu(player);
            }
        }
    }
}
