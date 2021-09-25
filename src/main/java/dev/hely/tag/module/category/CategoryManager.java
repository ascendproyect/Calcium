package dev.hely.tag.module.category;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.tag.Neon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    private final List<Category> category;

    public CategoryManager(){
        this.category = new ArrayList<>();
        onLoad();
    }

    public void onLoad(){
        this.category.clear();
        for(String category: Neon.getPlugin().getConfig().getConfigurationSection("categorys").getKeys(false)){
            String displayname = Neon.getPlugin().getConfig().getString("category." + category + ".displayname");
            List<String> lore = Neon.getPlugin().getConfig().getStringList("category." + category + ".lore");
            ItemStack item = ItemMaker.of(Material.getMaterial(Neon.getPlugin().getConfig().getInt("category." + category + ".item")))
                    .amount(Neon.getPlugin().getConfig().getInt("category." + category + ".amount"))
                    .data((short) Neon.getPlugin().getConfig().getInt("category." + category + ".data")).build();
            int slot = Neon.getPlugin().getConfig().getInt("category." + category + ".slot");

            String title = Neon.getPlugin().getConfig().getString("category." + category + ".menu.title");
            boolean fill = Neon.getPlugin().getConfig().getBoolean("category." + category + ".menu.fill.enabled");
            int fill_data = Neon.getPlugin().getConfig().getInt("category." + category + ".menu.fill.data");
            this.category.add(new Category(category, displayname, lore, item, slot, title, fill, fill_data));
        }
    }
    public void onDisable(){
        category.clear();
    }

    public List<Category> getCategory(){
        return category;
    }
}
