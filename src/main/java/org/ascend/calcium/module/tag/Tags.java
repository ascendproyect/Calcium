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
    private String displayName;
    private String category;
    private String permission;

    private List<String> alreadyActivatedLore;
    private List<String> activateLore;
    private List<String> noPermissionsLore;

    private int weight;
}
