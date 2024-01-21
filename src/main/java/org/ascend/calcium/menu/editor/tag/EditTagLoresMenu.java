package org.ascend.calcium.menu.editor.tag;

import com.cryptomorin.xseries.XSound;
import dev.hely.lib.CC;
import dev.hely.lib.callback.Callback;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.button.implement.BackButton;
import lombok.AllArgsConstructor;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.menu.editor.tag.buttons.EditTagPrefixButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: org.ascend.calcium.menu.editor.tag
//   Date: Saturday, January 20, 2024 - 5:03 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

@AllArgsConstructor
public class EditTagLoresMenu extends Menu {
    Tags tag;

    {
        setPlaceholder(true);
    }

    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getTitle(Player player) {
        return "Editing Lores Of: " + tag.getName();
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button = new HashMap<>();
        button.put(0, new BackButton(new EditTagMenu(tag)));

        button.put(11, new EquippedLoreButton(tag));
        button.put(13, new UnlockedLoreButton(tag));
        button.put(15, new NoPermissionLoreButton(tag));

        return button;
    }

    @AllArgsConstructor
    private static class EquippedLoreButton extends Button {
        Tags tag;
        private static int index;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.ICE).setDisplayName("&6Equipped Lore").setLore(getLore(player)).build();
        }

        public List<String> getLore(Player player) {
            final List<String> toReturn = new ArrayList<>();
            toReturn.add("");
            int i = 0;
            for (final String s : tag.getAlreadyActivatedLore()) {
                toReturn.add(CC.translate(((index == i) ? "&a" : "&7") + i + ". &f" + CC.translate(s).replace("%tag_display%", tag.getDisplayName()).replace("%player_name%", player.getName())));
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
            String currentLine = tag.getAlreadyActivatedLore().isEmpty() ? "" : tag.getAlreadyActivatedLore().get(index);
            if (clickType.equals(ClickType.MIDDLE)) {
                player.closeInventory();
                TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getAlreadyActivatedLore().remove(currentLine);
                    tag.getAlreadyActivatedLore().add(index, String.valueOf(callback));
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (!clickType.isShiftClick()) {
                XSound.play(player.getLocation(), "WOOD_CLICK");
                ++index;
                if (index >= tag.getAlreadyActivatedLore().size()) {
                    index = 0;
                }
                return;
            }
            if (clickType.isLeftClick()) {
                player.closeInventory();
                final TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getAlreadyActivatedLore().add(callback);
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                final ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (clickType.isRightClick()) {
                tag.getAlreadyActivatedLore().remove(currentLine);
                Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                --index;
                if (index >= tag.getAlreadyActivatedLore().size()) {
                    index = 0;
                }
            }
        }

        @Override
        public boolean toUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

    @AllArgsConstructor
    private static class UnlockedLoreButton extends Button {
        Tags tag;
        private static int index;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.STICK).setDisplayName("&6Unlocked Lore").setLore(getLore(player)).build();
        }

        public List<String> getLore(Player player) {
            final List<String> toReturn = new ArrayList<>();
            toReturn.add("");
            int i = 0;
            for (final String s : tag.getActivateLore()) {
                toReturn.add(CC.translate(((index == i) ? "&a" : "&7") + i + ". &f" + CC.translate(s).replace("%tag_display%", tag.getDisplayName()).replace("%player_name%", player.getName())));
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
            String currentLine = tag.getActivateLore().isEmpty() ? "" : tag.getActivateLore().get(index);
            if (clickType.equals(ClickType.MIDDLE)) {
                player.closeInventory();
                TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getActivateLore().remove(currentLine);
                    tag.getActivateLore().add(index, String.valueOf(callback));
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (!clickType.isShiftClick()) {
                XSound.play(player.getLocation(), "WOOD_CLICK");
                ++index;
                if (index >= tag.getAlreadyActivatedLore().size()) {
                    index = 0;
                }
                return;
            }
            if (clickType.isLeftClick()) {
                player.closeInventory();
                final TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getActivateLore().add(callback);
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                final ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (clickType.isRightClick()) {
                tag.getActivateLore().remove(currentLine);
                Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                --index;
                if (index >= tag.getActivateLore().size()) {
                    index = 0;
                }
            }
        }

        @Override
        public boolean toUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

    @AllArgsConstructor
    private static class NoPermissionLoreButton extends Button {
        Tags tag;
        private static int index;

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.REDSTONE_BLOCK).setDisplayName("&6No Permission Lore").setLore(getLore(player)).build();
        }

        public List<String> getLore(Player player) {
            final List<String> toReturn = new ArrayList<>();
            toReturn.add("");
            int i = 0;
            for (final String s : tag.getNoPermissionsLore()) {
                toReturn.add(CC.translate(((index == i) ? "&a" : "&7") + i + ". &f" + CC.translate(s).replace("%tag_display%", tag.getDisplayName()).replace("%player_name%", player.getName())));
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
            String currentLine = tag.getNoPermissionsLore().isEmpty() ? "" : tag.getNoPermissionsLore().get(index);
            if (clickType.equals(ClickType.MIDDLE)) {
                player.closeInventory();
                TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getNoPermissionsLore().remove(currentLine);
                    tag.getNoPermissionsLore().add(index, String.valueOf(callback));
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (!clickType.isShiftClick()) {
                XSound.play(player.getLocation(), "WOOD_CLICK");
                ++index;
                if (index >= tag.getAlreadyActivatedLore().size()) {
                    index = 0;
                }
                return;
            }
            if (clickType.isLeftClick()) {
                player.closeInventory();
                final TextChangePrompt stringPrompt = new TextChangePrompt(tag, (callback -> {
                    tag.getNoPermissionsLore().add(callback);
                    Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                }));
                final ConversationFactory factory = new ConversationFactory(Calcium.getInstance()).withFirstPrompt(stringPrompt).withLocalEcho(false);
                player.beginConversation(factory.buildConversation(player));
                return;
            }
            if (clickType.isRightClick()) {
                tag.getNoPermissionsLore().remove(currentLine);
                Calcium.getInstance().getModuleManager().getTags().saveTag(tag.getName());
                --index;
                if (index >= tag.getNoPermissionsLore().size()) {
                    index = 0;
                }
            }
        }

        @Override
        public boolean toUpdate(Player player, int slot, ClickType clickType) {
            return true;
        }
    }

    @AllArgsConstructor
    public static class TextChangePrompt extends StringPrompt {
        private Tags tag;
        private Callback<String> callback;

        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.GOLD + "[Calcium] " + ChatColor.YELLOW + "Please type the text for the new lore line for '" + ChatColor.WHITE + tag.getName() + ChatColor.YELLOW + "' tag. Type " + ChatColor.RED + "cancel" + ChatColor.YELLOW + " to cancel.";
        }

        public Prompt acceptInput(final ConversationContext conversationContext, final String input) {
            final Player sender = (Player)conversationContext.getForWhom();
            if (input.equalsIgnoreCase("cancel")) {
                new EditTagMenu(tag).openMenu(sender.getPlayer());
                conversationContext.getForWhom().sendRawMessage(CC.translate("&6[Calcium] &cYou have cancelled the process of editing the tag's lore."));
                return EditTagPrefixButton.EditDisplayPrefixPrompt.END_OF_CONVERSATION;
            }
            callback.callback(input);
            new EditTagLoresMenu(tag).openMenu(sender.getPlayer());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
