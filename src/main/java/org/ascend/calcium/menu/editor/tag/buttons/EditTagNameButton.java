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
public class EditTagNameButton extends Button {
    Tags tag;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.ICE)
                .setDisplayName("&6Edit Name")
                .setLore(
                        "&7This will allow you to change the",
                        "&7name of this chat prefix.",
                        "",
                        "&6- &7Current Name: &a" + tag.getName(),
                        "",
                        "&6Left-Click &7to edit this prefix's name."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new EditNamePrompt(this.tag)).withLocalEcho(false);
        player.beginConversation(factory.buildConversation(player));
    }

    @AllArgsConstructor
    public static class EditNamePrompt extends StringPrompt {
        private Tags tag;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the new name for the '" + ChatColor.WHITE + tag.getName() + ChatColor.YELLOW + "' tag. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                new EditTagMenu(tag).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the tag's name."));
                return EditNamePrompt.END_OF_CONVERSATION;
            }
            conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &aYou have successfully updated the name of " + this.tag.getName() + " &ato &6" + input + "&a."));
            this.tag.setName(CC.translate(input));
            new EditTagMenu(tag).openMenu(sender.getPlayer());
            Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
