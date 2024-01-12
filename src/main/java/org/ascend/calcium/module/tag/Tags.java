package org.ascend.calcium.module.tag;

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
public class Tags {

    private ItemStack item;

    private String name;
    private String displayname;
    private String category;
    private String perm;

    private List<String> equiped;
    private List<String> equip;
    private List<String> no_perm;

    private int slot;
}
