package org.ascend.calcium.menu.edit;

import dev.hely.lib.CC;
import dev.hely.lib.maker.ItemMaker;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.configuration.Configuration;
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
    public String getName() {
        return "editmenu";
    }
    @Override
    public String getTitle(Player player) {
        return "&8Menu Edit";
    }

    @Override
    public Map<Integer, Button> getMenuContent(Player player) {
        Map<Integer, Button> button= new HashMap<>();
        button.put(0, new TitleButton());
        button.put(1, new RawButton());
        button.put(2, new FillButton());
        button.put(3, new FillSetButton());
        return button;
    }

    private static class TitleButton extends Button{

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.SIGN).setDisplayName("&6&lEdit Menu Title")
                    .setLore("&7Click here to update this menu title!", "", "&6Current Menu Title: " + Configuration.Menu_Title).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
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
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("settings.menu.title", event.getMessage());
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().onReload();
                edit.remove(player.getUniqueId());
                new EditMenu().openMenu(player);
            }else if(edit.get(player.getUniqueId()).equalsIgnoreCase("category")){
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("categorys."+category.get(player.getUniqueId())+".menu.title", event.getMessage());
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
                category.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&aMenu title changed successfully"));
            }else if(edit.get(player.getUniqueId()).equalsIgnoreCase("categoryname")){
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("categorys."+category.get(player.getUniqueId())+".displayname", event.getMessage());
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
                category.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&aThe name of the category was changed correctly"));
            }
        }
    }

    private static class RawButton extends Button{

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.ITEM_FRAME).setDisplayName("&6&lEdit Menu Size")
                    .setLore("&7Click here to update this menu size!", "", "&6Current Size: &f" + Configuration.Menu_Raw).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            new SizeMenu().openMenu(player);
        }
    }

    private static class FillButton extends Button{

        @Override
        public ItemStack getItemStack(Player player) {

            return ItemMaker.of(Material.LEVER).setDisplayName("&6&lEdit Menu Fill")
                        .setLore("&7Click here to update this menu fill!", "",
                                "&6Current Menu Fill: " + (Configuration.Menu_Fill ? "&aEnabled" : "&cDisabled")).build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            FileConfiguration config = Calcium.getInstance().getConfig();
            if(Configuration.Menu_Fill){
                config.set("settings.menu.fill.enabled", false);
            }else{
                config.set("settings.menu.fill.enabled", true);
            }
            Calcium.getInstance().saveConfig();
            Calcium.getInstance().onReload();
            new EditMenu().openMenu(player);
        }
    }

    private static class FillSetButton extends Button{

        @Override
        public ItemStack getItemStack(Player player) {
            return ItemMaker.of(Material.STAINED_GLASS_PANE)
                    .setData((short) Configuration.Menu_FillData).setDisplayName("&6&lEdit Menu Fill Color")
                    .setLore("&7Click here to update this menu fill data!", "", "&6Current Fill Data: &f" + Configuration.Menu_FillData)
                    .build();
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
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
                FileConfiguration config = Calcium.getInstance().getConfig();
                config.set("categorys." + edit.get(player.getUniqueId()) + ".item", event.getItem().getTypeId());
                config.set("categorys." + edit.get(player.getUniqueId()) + ".data", event.getItem().getData().getData());
                config.set("categorys." + edit.get(player.getUniqueId()) + ".amount", event.getItem().getAmount());
                Calcium.getInstance().saveConfig();
                Calcium.getInstance().getModuleManager().getCategory().onLoad();
                edit.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&aCategory item changed successfully"));
            }
        }
    }
}
