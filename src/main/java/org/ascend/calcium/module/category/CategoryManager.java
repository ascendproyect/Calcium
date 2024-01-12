package org.ascend.calcium.module.category;

import dev.hely.lib.maker.ItemMaker;
import org.ascend.calcium.Calcium;
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
        for(String category: Calcium.getInstance().getConfig().getConfigurationSection("categorys").getKeys(false)){
            String displayname = Calcium.getInstance().getConfig().getString("categorys." + category + ".displayname");
            List<String> lore = Calcium.getInstance().getConfig().getStringList("categorys." + category + ".lore");
            ItemStack item = ItemMaker.of(Material.getMaterial(Calcium.getInstance().getConfig().getInt("categorys." + category + ".item")))
                    .setAmount(Calcium.getInstance().getConfig().getInt("categorys." + category + ".amount"))
                    .setData((short) Calcium.getInstance().getConfig().getInt("categorys." + category + ".data")).build();
            int slot = Calcium.getInstance().getConfig().getInt("categorys." + category + ".slot");

            String title = Calcium.getInstance().getConfig().getString("categorys." + category + ".menu.title");
            this.category.add(new Category(category, displayname, lore, item, slot, title));
        }
    }
    public void onDisable(){
        category.clear();
    }

    public List<Category> getCategory(){
        return category;
    }
}
