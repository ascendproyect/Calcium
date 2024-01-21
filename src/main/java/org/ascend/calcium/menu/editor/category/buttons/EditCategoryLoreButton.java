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

import com.cryptomorin.xseries.XSound;
import dev.hely.lib.CC;
import dev.hely.lib.callback.Callback;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.editor.category.EditCategoryMenu;
import org.ascend.calcium.menu.editor.tag.buttons.EditTagPrefixButton;
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

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class EditCategoryLoreButton extends Button {
    Category category;
    private static int index;

    @Override
    public ItemStack getItemStack(Player player) {
        return ItemMaker.of(Material.GLOWSTONE)
                .setDisplayName("&6Edit Category Lore")
                .setLore(getLore())
                .build();
    }

    public List<String> getLore() {
        final List<String> toReturn = new ArrayList<>();
        toReturn.add("");
        int i = 0;
        for (final String s : category.getLore()) {
            toReturn.add(CC.translate(((index == i) ? "&a" : "&7") + i + ". &f" + CC.translate(s)));
            ++i;
        }
        toReturn.add("");
        toReturn.add(CC.translate("&6Shift + Left-Click &7to add a line."));
        toReturn.add(CC.translate("&6Shift + Right-Click &7to delete this line."));
        toReturn.add(CC.translate("&6Left / Right-Click &7to scroll lines."));
        toReturn.add(CC.translate("&6Middle-Click &7to edit this line."));
        return toReturn;
    }

    @Override
    public void onClick(Player player, int slot, ClickType clickType) {
        String currentLine = category.getLore().isEmpty() ? "" : category.getLore().get(index);
        if (clickType.equals(ClickType.MIDDLE)) {
            player.closeInventory();
            TextChangePrompt stringPrompt = new TextChangePrompt(category, (callback -> {
                category.getLore().remove(currentLine);
                category.getLore().add(index, String.valueOf(callback));
                Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
            }));
            ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
            player.beginConversation(factory.buildConversation(player));
            return;
        }
        if (!clickType.isShiftClick()) {
            XSound.play(player.getLocation(), "WOOD_CLICK");
            ++index;
            if (index >= category.getLore().size()) {
                index = 0;
            }
            return;
        }
        if (clickType.isLeftClick()) {
            player.closeInventory();
            final TextChangePrompt stringPrompt = new TextChangePrompt(category, (callback -> {
                category.getLore().add(callback);
                Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
            }));
            final ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
            player.beginConversation(factory.buildConversation(player));
            return;
        }
        if (clickType.isRightClick()) {
            category.getLore().remove(currentLine);
            Calcium.getInstance().getModuleManager().getCategory().saveCategory(category.getName());
            --index;
            if (index >= category.getLore().size()) {
                index = 0;
            }
        }
    }

    @Override
    public boolean toUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }

    @AllArgsConstructor
    public static class TextChangePrompt extends StringPrompt {
        private Category category;
        private Callback<String> callback;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the text for the new lore line for '" + ChatColor.WHITE + category.getName() + ChatColor.YELLOW + "' category. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                new EditCategoryMenu(category).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the categories lore."));
                return EditTagPrefixButton.EditDisplayPrefixPrompt.END_OF_CONVERSATION;
            }
            callback.callback(input);
            new EditCategoryMenu(category).openMenu(sender.getPlayer());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
