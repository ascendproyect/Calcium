package org.ascend.calcium.menu.editor.category.buttons;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.tag.buttons
//   Date: Saturday, January 20, 2024 - 4:02 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.editor.category.EditCategoryMenu;
import org.ascend.calcium.module.category.Category;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class EditCategoryMaterialButton extends Button {
    Category category;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(category.getItem().getType())
                .setDisplayName("&6Edit Material")
                .setLore(
                        "&7This will allow you to change the",
                        "&7display material of this category.",
                        "",
                        "&6- &7Current Material: &a" + category.getItem().getType().name().toUpperCase(),
                        "",
                        "&6Left-Click &7to edit this categories material."
                )
                .setData(category.getItem().getData().getData())
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new EditMaterialPrompt(this.category)).withLocalEcho(false);
        player.beginConversation(factory.buildConversation(player));
    }

    @AllArgsConstructor
    public static class EditMaterialPrompt extends StringPrompt {
        private Category category;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please hold the new material for this category and type '" + ChatColor.WHITE + "confirm" + ChatColor.YELLOW + "'. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (!input.equalsIgnoreCase("confirm")) {
                new EditCategoryMenu(category).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the categories material."));
                return EditMaterialPrompt.END_OF_CONVERSATION;
            }
            ItemStack itemStack = sender.getItemInHand().clone();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou must be holding a valid item."));
                new EditCategoryMenu(category).openMenu(sender.getPlayer());
                return EditMaterialPrompt.END_OF_CONVERSATION;
            }

            conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &aYou have successfully updated the display material of " + this.category.getName() + " &ato &6" + input + "&a."));
            this.category.setItem(itemStack);
            Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
            new EditCategoryMenu(category).openMenu(sender.getPlayer());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
