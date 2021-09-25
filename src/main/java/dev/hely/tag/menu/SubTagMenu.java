package dev.hely.tag.menu;

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import dev.hely.tag.module.category.Category;
import dev.hely.tag.module.tag.Tags;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SubTagMenu extends Menu {

    private final Category category;

    public SubTagMenu(Category category){
        this.category = category;
    }

    @Override
    public String getTitle(Player player) {
        return category.getTitle();
    }


    @Override
    public void onClose(Player player) {
        new TagMenu().openMenu(player);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        if(category.isFill()){
            for (int i = 0; i < getSize(); ++i) {
                button.put(i, new SeparatorButton(category));
            }
        }

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
            return ItemMaker.of(tags.getItem().getType()).data(tags.getItem().getData().getData())
                    .amount(tags.getItem().getAmount()).displayName(tags.getDisplayname()).lore(tags.getEquip())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        }
    }

    @AllArgsConstructor
    private static class SeparatorButton extends Button {

        private final Category category;

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.STAINED_GLASS_PANE).data((short) category.getFill_data()).displayName("")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        }
    }
}
