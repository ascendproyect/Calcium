package org.ascend.calcium.conversion;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.conversion
//   Date: Friday, January 12, 2024 - 11:37 AM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.maker.ItemMaker;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.configuration.Configuration;
import org.ascend.calcium.module.tag.Tags;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ConversionManager {
    public void merge(Logger log) {
        File deluxetagsFile = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/DeluxeTags/config.yml");
        FileConfiguration deluxetagsConfig = YamlConfiguration.loadConfiguration(deluxetagsFile);
        File eternaltagsFile = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/EternalTags/tags.yml");
        FileConfiguration eternaltagsConfig = YamlConfiguration.loadConfiguration(eternaltagsFile);

        if (Configuration.Auto_Convert) {
            ConfigurationSection eternaltagsSection = eternaltagsConfig.getConfigurationSection("tags");
            ConfigurationSection deluxeTagsSection = deluxetagsConfig.getConfigurationSection("deluxetags");

            if (deluxeTagsSection == null && eternaltagsSection == null) {
                log.warning("Conversion Error: Calcium only supports the conversion of DeluxeTags & EternalTags.");
                return;
            }

            if (eternaltagsSection != null) {
                for (String identifier : eternaltagsSection.getKeys(false)) {
                    if (Calcium.getInstance().getModuleManager().getTags().getTag(identifier) != null) {
                        String prefix = eternaltagsConfig.getString(String.format("tags.%s.tag", identifier));
                        String permission = eternaltagsConfig.getString(String.format("tags.%s.permission", identifier));
                        String material = eternaltagsConfig.getString(String.format("tags.%s.icon.material", identifier));

                        Calcium.getInstance().getModuleManager().getTags().createTag(identifier);
                        Tags tag = Calcium.getInstance().getModuleManager().getTags().getTag(identifier);
                        tag.setDisplayName(prefix);
                        tag.setPermission(permission);
                        tag.setItem(ItemMaker.of(Material.valueOf(material)).setData((short) 0).setAmount(1).build());
                        Calcium.getInstance().getModuleManager().getTags().saveTag(identifier);
                    }
                }
                log.info("Conversion: Added all new chat tags from EternalTags, any existing chat tags with the same name where not added.");
            }

            if (deluxeTagsSection != null) {
                for (String identifier : deluxeTagsSection.getKeys(false)) {
                    if (Calcium.getInstance().getModuleManager().getTags().getTag(identifier) != null) {
                        String prefix = deluxetagsConfig.getString(String.format("deluxetags.%s.tag", identifier));
                        String permission = deluxetagsConfig.getString(String.format("deluxetags.%s.permission", identifier));

                        Calcium.getInstance().getModuleManager().getTags().createTag(identifier);
                        Tags tag = Calcium.getInstance().getModuleManager().getTags().getTag(identifier);
                        tag.setDisplayName(prefix);
                        tag.setPermission(permission);
                        Calcium.getInstance().getModuleManager().getTags().saveTag(identifier);
                    }
                }
                log.info("Conversion: Added all new chat tags from DeluxeTags, any existing chat tags with the same name where not added.");
            }
        }
    }
}
