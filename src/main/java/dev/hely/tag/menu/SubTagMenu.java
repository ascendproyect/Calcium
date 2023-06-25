package dev.hely.tag.menu;

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.implement.PaginatedMenu;
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

        Neon.getInstance().getModuleManager().getTags().getTags().forEach(tags -> {
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
        public ItemStack getItemStack(Player player) {
            List<String> lore = new ArrayList<>();
            if(player.hasPermission(tags.getPerm())
                    && Neon.getInstance().getProfileManager().getStorage().getTag(player.getUniqueId()).equalsIgnoreCase(tags.getDisplayname())){
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
            return ItemMaker.of(tags.getItem().getType()).setData(tags.getItem().getData().getData())
                    .setAmount(tags.getItem().getAmount()).setDisplayName(tags.getDisplayname()).setLore(lore)
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            if(player.hasPermission(tags.getPerm())){
                Neon.getInstance().getProfileManager().getStorage().setTag(player.getUniqueId(), tags.getDisplayname());

                player.closeInventory();

                for(String msg: Configuration.Select_Tag){
                    player.sendMessage(CC.translate(msg));
                }
            }
        }
    }
}
