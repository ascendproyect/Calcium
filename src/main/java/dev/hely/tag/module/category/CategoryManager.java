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
        for(String category: Neon.getInstance().getConfig().getConfigurationSection("categorys").getKeys(false)){
            String displayname = Neon.getInstance().getConfig().getString("categorys." + category + ".displayname");
            List<String> lore = Neon.getInstance().getConfig().getStringList("categorys." + category + ".lore");
            ItemStack item = ItemMaker.of(Material.getMaterial(Neon.getInstance().getConfig().getInt("categorys." + category + ".item")))
                    .setAmount(Neon.getInstance().getConfig().getInt("categorys." + category + ".amount"))
                    .setData((short) Neon.getInstance().getConfig().getInt("categorys." + category + ".data")).build();
            int slot = Neon.getInstance().getConfig().getInt("categorys." + category + ".slot");

            String title = Neon.getInstance().getConfig().getString("categorys." + category + ".menu.title");
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
