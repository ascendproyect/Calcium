package dev.hely.tag.menu;

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.PaginatedMenu;
import dev.hely.tag.Neon;
import dev.hely.tag.configuration.Configuration;
import dev.hely.tag.module.category.Category;
import dev.hely.tag.module.tag.Tags;
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
    public String getPrePaginatedTitle(Player player) {
        return category.getTitle();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> button = new HashMap<>();

        Neon.getPlugin().getModuleManager().getTags().getTags().forEach(tags -> {
            if(tags.getCategory().equalsIgnoreCase(category.getName())){
                button.put(tags.getSlot() - 1, new TagsButton(tags));
            }
        });
        return button;
    }

    @AllArgsConstructor
    private static class TagsButton extends Button {

        private final Tags tags;

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = new ArrayList<>();
            if(player.hasPermission(tags.getPerm())
                    && Neon.getPlugin().getProfileManager().getStorage().getTag(player.getUniqueId()).equalsIgnoreCase(tags.getDisplayname())){
                for(String l:tags.getEquiped()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayname()));
                }
            }else if(player.hasPermission(tags.getPerm())){
                for(String l:tags.getEquip()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayname()));
                }
            }else{
                for(String l:tags.getNo_perm()){
                    lore.add(l.replace("%player_name%", player.getName())
                            .replace("%tag_display%", tags.getDisplayname()));
                }
            }
            return ItemMaker.of(tags.getItem().getType()).data(tags.getItem().getData().getData())
                    .amount(tags.getItem().getAmount()).displayName(tags.getDisplayname()).lore(lore)
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if(player.hasPermission(tags.getPerm())){
                Neon.getPlugin().getProfileManager().getStorage().setTag(player.getUniqueId(), tags.getDisplayname());
                player.closeInventory();
                for(String msg: Configuration.Select_Tag){
                    player.sendMessage(CC.translate(msg));
                }
            }
        }
    }
}
