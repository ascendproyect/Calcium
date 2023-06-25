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
        Select_Tag = Neon.getInstance().getConfig().getStringList("settings.selected_tag");
        Menu_Title = Neon.getInstance().getConfig().getString("settings.menu.title");
        Menu_Raw = Neon.getInstance().getConfig().getInt("settings.menu.raw");
        Menu_Fill = Neon.getInstance().getConfig().getBoolean("settings.menu.fill.enabled");
        Menu_FillData = Neon.getInstance().getConfig().getInt("settings.menu.fill.data");
    }
}
