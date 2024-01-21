package org.ascend.calcium.menu.editor.tag.buttons;

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
import org.ascend.calcium.menu.editor.tag.EditTagMenu;
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

@AllArgsConstructor
public class EditTagMaterialButton extends Button {
    Tags tag;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(tag.getItem().getType())
                .setDisplayName("&6Edit Material")
                .setLore(
                        "&7This will allow you to change the",
                        "&7display material of this chat prefix.",
                        "",
                        "&6- &7Current Material: &a" + tag.getItem().getType().name().toUpperCase(),
                        "",
                        "&6Left-Click &7to edit this prefix's display material."
                )
                .setData(tag.getItem().getData().getData())
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new EditMaterialPrompt(this.tag)).withLocalEcho(false);
        player.beginConversation(factory.buildConversation(player));
    }

    @AllArgsConstructor
    public static class EditMaterialPrompt extends StringPrompt {
        private Tags tag;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please hold the new material for this tag and type '" + ChatColor.WHITE + "confirm" + ChatColor.YELLOW + "'. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (!input.equalsIgnoreCase("confirm")) {
                new EditTagMenu(tag).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the tag's material."));
                return EditMaterialPrompt.END_OF_CONVERSATION;
            }
            ItemStack itemStack = sender.getItemInHand().clone();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou must be holding a valid item."));
                new EditTagMenu(tag).openMenu(sender.getPlayer());
                return EditMaterialPrompt.END_OF_CONVERSATION;
            }

            conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &aYou have successfully updated the display material of " + this.tag.getName() + " &ato &6" + input + "&a."));
            this.tag.setItem(itemStack);
            Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
            new EditTagMenu(tag).openMenu(sender.getPlayer());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
