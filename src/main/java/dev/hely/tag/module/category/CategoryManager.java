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
            String displayname = Neon.getPlugin().getConfig().getString("categorys." + category + ".displayname");
            List<String> lore = Neon.getPlugin().getConfig().getStringList("categorys." + category + ".lore");
            ItemStack item = ItemMaker.of(Material.getMaterial(Neon.getPlugin().getConfig().getInt("categorys." + category + ".item")))
                    .amount(Neon.getPlugin().getConfig().getInt("categorys." + category + ".amount"))
                    .data((short) Neon.getPlugin().getConfig().getInt("categorys." + category + ".data")).build();
            int slot = Neon.getPlugin().getConfig().getInt("categorys." + category + ".slot");

            String title = Neon.getPlugin().getConfig().getString("categorys." + category + ".menu.title");
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
