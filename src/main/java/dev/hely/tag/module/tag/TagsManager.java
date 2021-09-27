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
        for(String tag: Neon.getPlugin().getTagsConfig().getConfig().getConfigurationSection("tags").getKeys(false)){
            ItemStack item = ItemMaker.of(Material.getMaterial(Neon.getPlugin().getTagsConfig().getConfig().getInt("tags."+tag+".item")))
                    .data((short) Neon.getPlugin().getTagsConfig().getConfig().getInt("tags."+tag+".data"))
                    .amount(Neon.getPlugin().getTagsConfig().getConfig().getInt("tags."+tag+".amount"))
                    .build();
            String displayname = Neon.getPlugin().getTagsConfig().getConfig().getString("tags."+tag+".displayname");
            String category = Neon.getPlugin().getTagsConfig().getConfig().getString("tags."+tag+".category");
            String perm = Neon.getPlugin().getTagsConfig().getConfig().getString("tags."+tag+".permission");

            List<String> equipped = Neon.getPlugin().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equipped");
            List<String> equip = Neon.getPlugin().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equip");
            List<String> no_perm = Neon.getPlugin().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.no_perm");

            int slot = Neon.getPlugin().getTagsConfig().getConfig().getInt("tags."+tag+".slot");

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
