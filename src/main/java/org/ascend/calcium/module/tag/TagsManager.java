package org.ascend.calcium.module.tag;

import dev.hely.lib.configuration.Config;
import dev.hely.lib.maker.ItemMaker;
import org.ascend.calcium.Calcium;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagsManager {


    private final List<Tags> tags;

    public TagsManager(){
        this.tags = new ArrayList<>();
        this.onLoad();
    }

    public void onLoad(){
        this.tags.clear();
        for(String tag: Calcium.getInstance().getTagsConfig().getConfig().getConfigurationSection("tags").getKeys(false)){
            ItemStack item = ItemMaker.of(Material.getMaterial(Calcium.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".item"))).setData((short) Calcium.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".data")).setAmount(Calcium.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".amount")).build();
            String displayname = Calcium.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".displayname");
            List<String> description = Calcium.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".description");
            String category = Calcium.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".category");
            String perm = Calcium.getInstance().getTagsConfig().getConfig().getString("tags."+tag+".permission");

            List<String> equipped = Calcium.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equipped");
            List<String> equip = Calcium.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.equip");
            List<String> no_perm = Calcium.getInstance().getTagsConfig().getConfig().getStringList("tags."+tag+".lore.no_perm");

            int slot = Calcium.getInstance().getTagsConfig().getConfig().getInt("tags."+tag+".slot");

            this.tags.add(new Tags(item, tag, displayname, description, category, perm, equipped, equip, no_perm, slot));
        }
    }

    public void createTag(String identifier) {
        if (getTag(identifier) != null) { // we do another check JUSt to be sure :p
            ItemStack item = ItemMaker.of(Material.getMaterial(421)).setData((short) 0).setAmount(0).build();
            String displayname = "&6" + identifier;
            List<String> description = Arrays.asList("Default description.");
            String category = "common";
            String perm = "calcium." + identifier;
            List<String> equipped = Arrays.asList(
                    "&7&m----------------------------",
                    "&fDisplay&7: &f%tag_display% &e%player_name%",
                    "",
                    "&fStatus&7: &aUnlocked",
                    "",
                    "&7You are using this tag.",
                    "&7&m----------------------------"
            );
            List<String> equip = Arrays.asList(
                    "&7&m----------------------------",
                    "&fDisplay&7: &f%tag_display% &e%player_name%",
                    "",
                    "&fStatus&7: &aUnlocked",
                    "",
                    "&6Left-Click &7to activate this chat tag.",
                    "&7&m----------------------------"
            );
            List<String> no_perm = Arrays.asList(
                    "&7&m----------------------------",
                    "&fDisplay&7: &f%tag_display% &e%player_name%",
                    "",
                    "&fStatus&7: &cLocked",
                    "",
                    "&cPurchase this chat prefix from our store!",
                    "&c&ostore.ascendproyect.org",
                    "&7&m----------------------------"
            );
            int slot = tags.size() + 1;

            //Now we add it to the config:
            YamlConfiguration config = Calcium.getInstance().getTagsConfig().getConfiguration();
            config.set("tags." + identifier + ".item", 421);
            config.set("tags." + identifier + ".data", 0);
            config.set("tags." + identifier + ".amount", 1);
            config.set("tags." + identifier + ".slot", 1);
            config.set("tags." + identifier + ".displayname", displayname);
            config.set("tags." + identifier + ".category", category);
            config.set("tags." + identifier + ".permission", perm);
            config.set("tags." + identifier + ".lore.equipped", equipped);
            config.set("tags." + identifier + ".lore.equip", equip);
            config.set("tags." + identifier + ".lore.no_perm", no_perm);
            Calcium.getInstance().getTagsConfig().save();
            Calcium.getInstance().getTagsConfig().reload();

            this.tags.add(new Tags(item, identifier, displayname, description, category, perm, equipped, equip, no_perm, slot));
        }
    }

    public void saveTag(String identifier) {
        if (getTag(identifier) != null) {
            Tags tag = Calcium.getInstance().getModuleManager().getTags().getTag(identifier);
            YamlConfiguration config = Calcium.getInstance().getTagsConfig().getConfiguration();
            config.set("tags." + identifier + ".item", tag.getItem().getTypeId());
            config.set("tags." + identifier + ".data", tag.getItem().getData());
            config.set("tags." + identifier + ".amount", tag.getItem().getAmount());
            config.set("tags." + identifier + ".slot", tag.getWeight());
            config.set("tags." + identifier + ".displayname", tag.getDisplayName());
            config.set("tags." + identifier + ".category", tag.getCategory());
            config.set("tags." + identifier + ".permission", tag.getPermission());
            config.set("tags." + identifier + ".lore.equipped", tag.getAlreadyActivatedLore());
            config.set("tags." + identifier + ".lore.equip", tag.getActivateLore());
            config.set("tags." + identifier + ".lore.no_perm", tag.getNoPermissionsLore());
            Calcium.getInstance().getTagsConfig().save();
            Calcium.getInstance().getTagsConfig().reload();
        }
    }

    public void deleteTag(String identifier) {
        if (getTag(identifier) != null) {
            tags.remove(getTag(identifier));

            try {
                Calcium.getInstance().getTagsConfig().getConfiguration().set("tags." + identifier, null);
                Calcium.getInstance().getTagsConfig().save();
                Calcium.getInstance().getTagsConfig().reload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDisable(){
        this.tags.clear();
    }

    public List<Tags> getTags() {
        return tags;
    }

    public Tags getTag(String tag) {
        Tags t = null;
        for (Tags tg : tags) {
            if (tg.getName().equalsIgnoreCase(tag)) {
                t = tg;
            }
        }

        return t;
    }
}
