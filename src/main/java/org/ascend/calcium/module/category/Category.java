package org.ascend.calcium.module.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    private String name;
    private String displayname;
    private List<String> lore;
    private ItemStack item;
    private int slot;

    private String title;

    //New Shit: 1/12/2024
    private boolean isDefault; // If it's the default category, all new tags automatically are assigned to it.
    private boolean isGlobal; // This means ALL tags will appear in the menu.
    private String bypassPermission; // If they have this permission, allow them to use any tag in the category.
    private String permission; // The permission required to open the category.
}
