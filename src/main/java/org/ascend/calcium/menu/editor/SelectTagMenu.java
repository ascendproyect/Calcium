package org.ascend.calcium.menu.editor;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor
//   Date: Friday, January 12, 2024 - 4:01 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.button.implement.BackButton;
import dev.hely.lib.menu.button.implement.InformationButton;
import dev.hely.lib.menu.button.implement.PageButton;
import dev.hely.lib.menu.implement.PaginatedMenu;
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

import java.util.HashMap;
import java.util.Map;

public class SelectTagMenu extends PaginatedMenu {
    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getTitle(Player player) {
        return "&8Select Tag";
    }

    public Map<Integer, Button> getGlobalContent(final Player player) {
        final Map<Integer, Button> toReturn = new HashMap<>();
        toReturn.put(0, new BackButton(new MainEditorMenu()));
        toReturn.put(1, Button.getPlaceholder());
        toReturn.put(2, Button.getPlaceholder());
        toReturn.put(3, Button.getPlaceholder());
        toReturn.put(4, new Button() {
            @Override
            public ItemStack getItemStack(Player player) {
                return ItemMaker.of(Material.PAPER).setDisplayName("&6New Chat Tag").addLore("&7Click here to create a new chat tag.").build();
            }

            public void onClick(final Player player, final int slot, final ClickType clickType) {
                player.closeInventory();
                final ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(new NamePrompt()).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
            }
        });
        toReturn.put(5, Button.getPlaceholder());
        toReturn.put(6, Button.getPlaceholder());
        toReturn.put(7, Button.getPlaceholder());
        toReturn.put(8, Button.getPlaceholder());
        toReturn.put(9, Button.getPlaceholder());
        toReturn.put(17, Button.getPlaceholder());
        toReturn.put(18, Button.getPlaceholder());
        toReturn.put(26, Button.getPlaceholder());
        toReturn.put(27, Button.getPlaceholder());
        toReturn.put(35, Button.getPlaceholder());
        toReturn.put(36, Button.getPlaceholder());
        toReturn.put(37, new PageButton(-1, this));
        toReturn.put(38, Button.getPlaceholder());
        toReturn.put(39, Button.getPlaceholder());
        toReturn.put(40, new InformationButton(this));
        toReturn.put(41, Button.getPlaceholder());
        toReturn.put(42, Button.getPlaceholder());
        toReturn.put(43, new PageButton(1, this));
        toReturn.put(44, Button.getPlaceholder());
        return toReturn;
    }

    @Override
    public Map<Integer, Button> getPaginatedContent(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        Calcium.getInstance().getModuleManager().getTags().getTags().forEach(tags -> {
            button.put(button.size(), new TagsButton(tags));
        });
        return button;
    }

    @AllArgsConstructor
    private static class TagsButton extends Button {
        private final Tags tag;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(tag.getItem().getType()).setData(tag.getItem().getData().getData())
                    .setAmount(tag.getItem().getAmount()).setDisplayName(tag.getDisplayName()).setLore("&7Click here to modify this tag.")
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new EditTagMenu(tag).openMenu(player);
        }
    }

    public static class NamePrompt extends StringPrompt {
        public String getPromptText(final ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the name for this new chat tag. Type '" + ChatColor.RED + "cancel" + ChatColor.YELLOW + "' to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                conversationContext.getForWhom().sendRawMessage(ChatColor.RED + "Tag creation process cancelled.");
                return NamePrompt.END_OF_CONVERSATION;
            }
            if (Calcium.getInstance().getModuleManager().getTags().getTag(input) != null) {
                conversationContext.getForWhom().sendRawMessage(ChatColor.RED + "Sorry, a tag with that name already exists.");
                return NamePrompt.END_OF_CONVERSATION;
            }
            Calcium.getInstance().getModuleManager().getTags().createTag(input);
            Calcium.getInstance().getModuleManager().getTags().saveTag(input);
            Calcium.getInstance().getTagsConfig().save();
            Calcium.getInstance().getTagsConfig().reload();

            new SelectTagMenu().openMenu(sender);
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
