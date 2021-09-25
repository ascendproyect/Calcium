package dev.hely.lib;

import org.bukkit.Bukkit;

import java.util.List;

/**
 * Created By LeandroSSJ
 * Created on 30/08/2021
 */
public class Messages {



    public static void sendMessage(String message) {
        if(message.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(message)));
    }

    public static void sendMessage(List<String> messages) {
        if(messages.isEmpty()) return;

        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(CC.translate(message)));
        Bukkit.getOnlinePlayers().forEach(player -> messages.forEach(message -> player.sendMessage(CC.translate(message))));
    }

    public static void sendMessage(String message, String permission) {
        if(message.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage(CC.translate(message));

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission))
                .forEach(player -> player.sendMessage(CC.translate(message)));
    }

    public static void sendMessage(List<String> messages, String permission) {
        if(messages.isEmpty()) return;

        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(CC.translate(message)));

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission))
                .forEach(player -> messages.forEach(message -> player.sendMessage(CC.translate(message))));
    }



}
