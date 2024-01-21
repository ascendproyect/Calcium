package org.ascend.calcium.module.category;

import dev.hely.lib.maker.ItemMaker;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.module.tag.Tags;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CategoryManager {

    private final List<Category> category;

    public CategoryManager(){
        this.category = new ArrayList<>();
        onLoad();
    }

    public void onLoad(){
        this.category.clear();

        if (Calcium.getInstance().getCategoriesConfig().getConfig().getConfigurationSection("categories") == null) {
            Calcium.getInstance().getLogger().severe("WARNING: We could not find any tags in the categories.yml file. Please make sure you have at least one category saved.");
            return;
        }
        for (String category : Calcium.getInstance().getCategoriesConfig().getConfig().getConfigurationSection("categories").getKeys(false)){
            String displayname = Calcium.getInstance().getCategoriesConfig().getConfig().getString("categories." + category + ".displayname");
            List<String> lore = Calcium.getInstance().getCategoriesConfig().getConfig().getStringList("categories." + category + ".lore");
            ItemStack item = ItemMaker.of(Material.getMaterial(Calcium.getInstance().getCategoriesConfig().getConfig().getInt("categories." + category + ".item"))).setAmount(Calcium.getInstance().getCategoriesConfig().getConfig().getInt("categories." + category + ".amount")).setData((short) Calcium.getInstance().getCategoriesConfig().getConfig().getInt("categories." + category + ".data")).build();
            int slot = Calcium.getInstance().getCategoriesConfig().getConfig().getInt("categories." + category + ".slot");
            String title = Calcium.getInstance().getCategoriesConfig().getConfig().getString("categories." + category + ".menu.title");

            //New Shit: 1/12/2024
            boolean isDefault = Calcium.getInstance().getCategoriesConfig().getConfig().getBoolean("categories." + category + ".settings.default");
            boolean isGlobal = Calcium.getInstance().getCategoriesConfig().getConfig().getBoolean("categories." + category + ".settings.global");
            String bypassPermission = Calcium.getInstance().getCategoriesConfig().getConfig().getString("categories." + category + ".settings.bypass-permission");
            String permission = Calcium.getInstance().getCategoriesConfig().getConfig().getString("categories." + category + ".settings.permission");

            this.category.add(new Category(category, displayname, lore, item, slot, title, isDefault, isGlobal, bypassPermission, permission));
        }
    }
    public void onDisable(){
        category.clear();
    }

    public Category getCategory(String category) {
        Category c = null;
        for (Category ct : this.category) {
            if (ct.getName().equalsIgnoreCase(category)) {
                c = ct;
            }
        }

        return c;
    }

    public void createCategory(String identifier) {
        String displayname = "&6" + identifier;
        List<String> lore = Arrays.asList("&7No lore setup.");
        ItemStack item = ItemMaker.of(Material.REDSTONE_BLOCK).build();
        int slot = category.size() + 1;

        //New Shit: 1/12/2024
        String bypassPermission = "calcium.admin." + identifier;
        String permission = "calcium." + identifier;

        //Now we add it to the config:
        YamlConfiguration config = Calcium.getInstance().getCategoriesConfig().getConfiguration();
        config.set("categories." + identifier + ".item", item.getTypeId());
        config.set("categories." + identifier + ".data", item.getData().getData());
        config.set("categories." + identifier + ".amount", 1);
        config.set("categories." + identifier + ".slot", slot);
        config.set("categories." + identifier + ".displayname", displayname);
        config.set("categories." + identifier + ".lore", lore);
        config.set("categories." + identifier + ".menu.title", identifier);
        config.set("categories." + identifier + ".settings.default", false);
        config.set("categories." + identifier + ".settings.global", false);
        config.set("categories." + identifier + ".settings.bypass-permission", bypassPermission);
        config.set("categories." + identifier + ".settings.permission", permission);

        Calcium.getInstance().getCategoriesConfig().save();
        Calcium.getInstance().getCategoriesConfig().reload();

        this.category.add(new Category(identifier, displayname, lore, item, slot, identifier, false, false, bypassPermission, permission));
    }

    public void saveCategory(String identifier) {
        Category category = Calcium.getInstance().getModuleManager().getCategory().getCategory(identifier);
        YamlConfiguration config = Calcium.getInstance().getCategoriesConfig().getConfiguration();
        config.set("categories." + identifier + ".item", category.getItem().getTypeId());
        config.set("categories." + identifier + ".data", category.getItem().getData().getData());
        config.set("categories." + identifier + ".amount", category.getItem().getAmount());
        config.set("categories." + identifier + ".slot", category.getItem().getAmount());
        config.set("categories." + identifier + ".displayname", category.getDisplayname());
        config.set("categories." + identifier + ".lore", category.getLore());
        config.set("categories." + identifier + ".menu.title", category.getTitle());
        config.set("categories." + identifier + ".settings.default", category.isDefault());
        config.set("categories." + identifier + ".settings.global", category.isGlobal());
        config.set("categories." + identifier + ".settings.bypass-permission", category.getBypassPermission());
        config.set("categories." + identifier + ".settings.permission", category.getPermission());

        Calcium.getInstance().getCategoriesConfig().save();
        Calcium.getInstance().getCategoriesConfig().reload();
    }

    public List<Category> getCategory(){
        return category;
    }
}
