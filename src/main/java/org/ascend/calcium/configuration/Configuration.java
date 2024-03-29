package org.ascend.calcium.configuration;

import org.ascend.calcium.Calcium;

import java.util.List;

public class Configuration {

    public static List<String> Select_Tag;
    public static String Menu_Title;
    public static int Menu_Raw;
    public static boolean Menu_Fill;
    public static int Menu_FillData;
    public static boolean Auto_Convert;

    public static List<String> Add_Favourite;
    public static List<String> Remove_Favourite;


    public static void loadConfig(){
        Select_Tag = Calcium.getInstance().getConfig().getStringList("settings.selected_tag");
        Menu_Title = Calcium.getInstance().getConfig().getString("settings.menu.title");
        Menu_Raw = Calcium.getInstance().getConfig().getInt("settings.menu.raw");
        Menu_Fill = Calcium.getInstance().getConfig().getBoolean("settings.menu.fill.enabled");
        Menu_FillData = Calcium.getInstance().getConfig().getInt("settings.menu.fill.data");

        Auto_Convert = Calcium.getInstance().getConfig().getBoolean("settings.conversions.auto-convert");


        Add_Favourite = Calcium.getInstance().getConfig().getStringList("settings.added_favourite");
        Remove_Favourite = Calcium.getInstance().getConfig().getStringList("settings.removed_favourite");
    }
}
