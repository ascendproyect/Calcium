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
}
