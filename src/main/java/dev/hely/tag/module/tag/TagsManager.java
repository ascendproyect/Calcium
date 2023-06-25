package dev.hely.tag.module.tag;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.tag.Neon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TagsManager {


    private final List<Tags> tags;

    public TagsManager(){
        this.tags = new ArrayList<>();
        this.onLoad();
    }

    public void onLoad(){
        this.tags.clear();
        for(String tag: Neon.getInstance().getTagsConfig().getConfig().getConfigurationSection("tags").getKeys(false)){
            ItemStack item = ItemMaker.of(Material.getMaterial(Neon.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".item")))
                    .setData((short) Neon.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".data"))
                    .setAmount(Neon.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".amount"))
                    .build();
            String displayname = Neon.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".displayname");
            String category = Neon.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".category");
            String perm = Neon.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".permission");

            List<String> equipped = Neon.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equipped");
            List<String> equip = Neon.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equip");
            List<String> no_perm = Neon.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.no_perm");

            int slot = Neon.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".slot");

            this.tags.add(new Tags(item, tag, displayname, category, perm, equipped, equip, no_perm, slot));
        }
    }


    public void onDisable(){
        this.tags.clear();
    }

    public List<Tags> getTags() {
        return tags;
    }
}
