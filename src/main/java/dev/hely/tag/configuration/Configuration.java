package dev.hely.tag.configuration;

import dev.hely.tag.Neon;

import java.util.List;

public class Configuration {

    public static List<String> Select_Tag;
    public static String Menu_Title;
    public static int Menu_Raw;
    public static boolean Menu_Fill;
    public static int Menu_FillData;

    public static void loadConfig(){
        Select_Tag = Neon.getPlugin().getConfig().getStringList("settings.selected_tag");
        Menu_Title = Neon.getPlugin().getConfig().getString("settings.menu.title");
        Menu_Raw = Neon.getPlugin().getConfig().getInt("settings.menu.raw");
        Menu_Fill = Neon.getPlugin().getConfig().getBoolean("settings.menu.fill.enabled");
        Menu_FillData = Neon.getPlugin().getConfig().getInt("settings.menu.fill.data");
    }
}
