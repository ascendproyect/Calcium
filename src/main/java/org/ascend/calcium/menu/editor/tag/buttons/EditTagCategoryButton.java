package org.ascend.calcium.menu.editor.tag.buttons;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.tag.buttons
//   Date: Saturday, January 20, 2024 - 4:03 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.button.implement.BackButton;
import dev.hely.lib.menu.button.implement.InformationButton;
import dev.hely.lib.menu.button.implement.PageButton;
import dev.hely.lib.menu.implement.PaginatedMenu;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.editor.tag.EditTagMenu;
import org.ascend.calcium.module.category.Category;
import org.ascend.calcium.module.tag.Tags;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditTagCategoryButton extends Button {
    Tags tag;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.BEACON)
                .setDisplayName("&6Edit Category")
                .setLore(
                        "&7This will allow you to change the",
                        "&7category the tag is shown in.",
                        "",
                        "&6- &7Current Category: &a" + tag.getCategory(),
                        "",
                        "&6Left-Click &7to edit this tag's category."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();

        new PaginatedMenu() {
            @Override
            public String getName() {
                return "categorySelector";
            }

            @Override
            public int getMaxPerPage(Player player) {
                return 18;
            }

            @Override
            public int getSize() {
                return 36;
            }

            @Override
            public String getTitle(Player player) {
                return "Select Category";
            }

            public Map<Integer, Button> getGlobalContent(Player player) {
                Map<Integer, Button> menuContent = new HashMap();
                menuContent.put(0, new BackButton(new EditTagMenu(tag)));
                menuContent.put(1, Button.getPlaceholder());
                menuContent.put(2, Button.getPlaceholder());
                menuContent.put(3, Button.getPlaceholder());
                menuContent.put(4, Button.getPlaceholder());
                menuContent.put(5, Button.getPlaceholder());
                menuContent.put(6, Button.getPlaceholder());
                menuContent.put(7, Button.getPlaceholder());
                menuContent.put(8, Button.getPlaceholder());
                menuContent.put(9, Button.getPlaceholder());
                menuContent.put(17, Button.getPlaceholder());
                menuContent.put(18, Button.getPlaceholder());
                menuContent.put(26, Button.getPlaceholder());
                menuContent.put(27, Button.getPlaceholder());
                menuContent.put(28, new PageButton(-1, this));
                menuContent.put(29, Button.getPlaceholder());
                menuContent.put(30, Button.getPlaceholder());
                menuContent.put(31, new InformationButton(this));
                menuContent.put(32, Button.getPlaceholder());
                menuContent.put(33, Button.getPlaceholder());
                menuContent.put(34, new PageButton(1, this));
                menuContent.put(35, Button.getPlaceholder());
                return menuContent;
            }

            @Override
            public Map<Integer, Button> getPaginatedContent(Player player) {
                Map<Integer, Button> toReturn= new HashMap<>();

                for (Category category : Calcium.getInstance().getModuleManager().getCategory().getCategory()) {
                    toReturn.put(toReturn.size(), new Button() {
                        @Override
                        public ItemStack getItemStack(Player player) {
                            if (shouldGlow()) {
                                return ItemMaker.of(category.getItem().getType()).setData(category.getItem().getData().getData()).setDisplayName(category.getDisplayname()).setLore(category.getLore()).addFakeGlow().build();
                            } else {
                                return ItemMaker.of(category.getItem().getType()).setData(category.getItem().getData().getData()).setDisplayName(category.getDisplayname()).setLore(category.getLore()).build();
                            }
                        }

                        public boolean shouldGlow() {
                            return tag.getCategory().equalsIgnoreCase(category.getName());
                        }

                        @Override
                        public void onClick(Player player, int slot, ClickType clickType) {
                            if (tag.getCategory().equalsIgnoreCase(category.getName())) {
                                player.sendMessage(CC.translate("&6[Calcium] &aThe '&f" + tag.getName() + "&a' category is already set to this."));
                                return;
                            }
                            tag.setCategory(category.getName());
                            Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                            player.closeInventory();
                            new EditTagMenu(tag).openMenu(player);
                        }
                    });
                }

                return toReturn;
            }


        }.openMenu(player);
    }


}
