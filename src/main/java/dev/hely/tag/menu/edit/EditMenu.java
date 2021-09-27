package dev.hely.tag.menu.edit;

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.tag.Neon;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EditMenu extends Menu implements Listener{

    public static final Map<UUID, String> edit=new HashMap<>();
    public static final Map<UUID, String> category=new HashMap<>();

    @Override
    public String getTitle(Player player) {
        return "&7Edit menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        button.put(0, new TitleButton());
        button.put(1, new RawButton());
        button.put(2, new FillButton());
        button.put(3, new FillSetButton());
        return button;
    }

    private static class TitleButton extends Button{

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.SIGN).displayName("&eTitle").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            player.closeInventory();
            player.sendMessage(CC.translate("&eWrite the new title."));
            player.sendMessage(CC.translate("&cWrite 'cancel' to cancel the edit."));
            edit.put(player.getUniqueId(), "main");
        }
    }

    @EventHandler
    public void onEdit(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(edit.containsKey(player.getUniqueId())){
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                edit.remove(player.getUniqueId());
                return;
            }
            if(edit.get(player.getUniqueId()).equalsIgnoreCase("main")){
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("settings.menu.main.title", event.getMessage());
                Neon.getPlugin().saveConfig();
                edit.remove(player.getUniqueId());
                new EditMenu().openMenu(player);
            }else
            if(edit.get(player.getUniqueId()).equalsIgnoreCase("category")){
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("categorys."+category.get(player.getUniqueId())+".menu.title", event.getMessage());
                Neon.getPlugin().saveConfig();
                Neon.getPlugin().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
                category.remove(player.getUniqueId());
                new EditMenu().openMenu(player);
            }else if(edit.get(player.getUniqueId()).equalsIgnoreCase("categoryname")){
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("categorys."+category.get(player.getUniqueId())+".displayname", event.getMessage());
                Neon.getPlugin().saveConfig();
                Neon.getPlugin().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
                category.remove(player.getUniqueId());
                new EditMenu().openMenu(player);
            }
        }
    }

    private static class RawButton extends Button{

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.ITEM_FRAME).displayName("&eSize").build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new SizeMenu().openMenu(player);
        }
    }

    private static class FillButton extends Button{

        @Override
        public ItemStack getButtonItem(Player player) {
            if(Neon.getPlugin().getConfig().getBoolean("settings.menu.main.fill.enabled")){
                return ItemMaker.of(Material.INK_SACK).displayName("&eFill").data((short) 10).build();
            }else{
                return ItemMaker.of(Material.INK_SACK).displayName("&eFill").data((short) 8).build();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            FileConfiguration config = Neon.getPlugin().getConfig();
            if(Neon.getPlugin().getConfig().getBoolean("settings.menu.main.fill.enabled")){
                config.set("settings.menu.main.fill.enabled", false);
            }else{
                config.set("settings.menu.main.fill.enabled", true);
            }
            Neon.getPlugin().saveConfig();
            new EditMenu().openMenu(player);
        }
    }

    private static class FillSetButton extends Button{

        @Override
        public ItemStack getButtonItem(Player player) {
            return ItemMaker.of(Material.STAINED_GLASS_PANE)
                    .data((short) Neon.getPlugin().getConfig().getInt("settings.menu.main.fill.data")).displayName("&7")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new FillMenu().openMenu(player);
        }
    }

    @EventHandler
    public void onSetItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(edit.containsKey(player.getUniqueId())){
            if (event.hasItem()) {
                if (event.getAction() == null) {
                    edit.remove(player.getUniqueId());
                    return;
                }
                if (event.useInteractedBlock() == Event.Result.DENY && event.useItemInHand() == Event.Result.DENY) {
                    edit.remove(player.getUniqueId());
                    return;
                }

                event.setCancelled(true);
                FileConfiguration config = Neon.getPlugin().getConfig();
                config.set("categorys." + edit.get(player.getUniqueId()) + ".item", event.getItem().getTypeId());
                config.set("categorys." + edit.get(player.getUniqueId()) + ".data", event.getItem().getData().getData());
                config.set("categorys." + edit.get(player.getUniqueId()) + ".amount", event.getItem().getAmount());
                Neon.getPlugin().saveConfig();
                Neon.getPlugin().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
            }
        }
    }
}
