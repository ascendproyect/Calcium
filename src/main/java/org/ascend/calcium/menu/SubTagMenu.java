package org.ascend.calcium.menu;

import org.ascend.calcium.Calcium;
import org.ascend.calcium.module.category.Category;
import org.ascend.calcium.module.tag.Tags;
import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.implement.PaginatedMenu;
import org.ascend.calcium.configuration.Configuration;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubTagMenu extends PaginatedMenu {

    private final Category category;

    public SubTagMenu(Category category){
        this.category = category;
    }

    @Override
    public String getName() {
        return "subtag";
    }
    @Override
    public String getTitle(Player player) {
        return category.getTitle();
    }

    @Override
    public Map<Integer, Button> getPaginatedContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();

        Calcium.getInstance().getModuleManager().getTags().getTags().forEach(tags -> {
            if(tags.getCategory().equalsIgnoreCase(category.getName())){
                button.put(tags.getWeight() - 1, new TagsButton(tags));
            }
        });
        return button;
    }

    @AllArgsConstructor
    private static class TagsButton extends Button {

        private final Tags tags;

        @Override
        public ItemStack getItemStack(Player player) {
            List<String> lore = new ArrayList<>();
            if(player.hasPermission(tags.getPermission())
                    && Calcium.getInstance().getProfileManager().getStorage().getTag(player.getUniqueId()).equalsIgnoreCase(tags.getDisplayName())){
                for(String l:tags.getAlreadyActivatedLore()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayName()));
                }
            }else if(player.hasPermission(tags.getPermission())){
                for(String l:tags.getActivateLore()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayName()));
                }
            }else{
                for(String l:tags.getNoPermissionsLore()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayName()));
                }
            }
            return ItemMaker.of(tags.getItem().getType()).setData(tags.getItem().getData().getData())
                    .setAmount(tags.getItem().getAmount()).setDisplayName(tags.getDisplayName()).setLore(lore)
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(player.hasPermission(tags.getPermission())){
                Calcium.getInstance().getProfileManager().getStorage().setTag(player.getUniqueId(), tags.getDisplayName());

                player.closeInventory();

                for(String msg: Configuration.Select_Tag){
                    player.sendMessage(CC.translate(msg));
                }
            }
        }
    }
}
