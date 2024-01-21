package org.ascend.calcium.menu.editor.plugin.buttons;

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
import org.ascend.calcium.configuration.Configuration;
import org.ascend.calcium.menu.editor.plugin.PluginEditorMenu;
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
public class EditMainMenuTitleButton extends Button {
    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.SIGN)
                .setDisplayName("&6Edit Title")
                .setLore(
                        "&7This will allow you to change the",
                        "&7title of the main menu.",
                        "",
                        "&6- &7Current Title: &a" + Configuration.Menu_Title,
                        "",
                        "&6Left-Click &7to edit this main menu title."
                )
                .build();
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        player.closeInventory();
        ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new EditNamePrompt()).withLocalEcho(false);
        player.beginConversation(factory.buildConversation(player));
    }

    @AllArgsConstructor
    public static class EditNamePrompt extends StringPrompt {
        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the new main menu title. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                new PluginEditorMenu().openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the main menu's title."));
                return EditNamePrompt.END_OF_CONVERSATION;
            }
            conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &aYou have successfully updated the main menu's title to &6" + input + "&a."));
            Calcium.getInstance().getConfig().set("settings.menu.title", input);
            Calcium.getInstance().saveConfig();
            Calcium.getInstance().reloadConfig();
            new PluginEditorMenu().openMenu(sender.getPlayer());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
