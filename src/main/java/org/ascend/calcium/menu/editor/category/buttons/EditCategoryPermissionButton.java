package org.ascend.calcium.menu.editor.category.buttons;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.category.buttons
//   Date: Saturday, January 20, 2024 - 5:56 PM
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
public class EditCategoryPermissionButton extends Button {
    Category category;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.STICK)
                .setDisplayName("&6Edit Permission")
                .setLore(
                        "&7This will allow you to change the",
                        "&7permission to be able to access",
                        "&7this category.",
                        "",
                        "&6- &7Current Permission: &a" + category.getPermission(),
                        "",
                        "&6Left-Click &7to edit this categories permission."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new EditNamePrompt(this.category)).withLocalEcho(false);
        player.beginConversation(factory.buildConversation(player));
    }

    @AllArgsConstructor
    public static class EditNamePrompt extends StringPrompt {
        private Category category;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the new permission for the '" + ChatColor.WHITE + category.getName() + ChatColor.YELLOW + "' category. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                new EditCategoryMenu(category).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the categories name."));
                return EditNamePrompt.END_OF_CONVERSATION;
            }
            conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &aYou have successfully updated the permission of " + this.category.getName() + " &ato &6" + input + "&a."));
            category.setPermission(CC.translate(input));
            new EditCategoryMenu(category).openMenu(sender.getPlayer());
            Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
