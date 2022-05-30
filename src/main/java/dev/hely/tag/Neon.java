package dev.hely.tag;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.hely.lib.command.CommandManager;
import dev.hely.lib.configuration.Config;
import dev.hely.lib.manager.Manager;
import dev.hely.lib.menu.MenuListener;
import dev.hely.tag.configuration.Configuration;
import dev.hely.tag.menu.edit.EditMenu;
import dev.hely.tag.module.ModuleManager;
import dev.hely.tag.profile.ProfileManager;
import dev.hely.tag.profile.storage.Mongo;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.StorageHook;
import dev.hely.tag.profile.storage.YML;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class Neon extends JavaPlugin {

    public static MongoCollection<Document> collection;
    private static Neon plugin;
    private List<Manager> managerList;
    private Config tags;
    private Config profile;
    private ModuleManager moduleManager;
    private ProfileManager profileManager;
    private StorageHook storageHook;

    @Override
    public void onLoad() {
        managerList = ImmutableList.of(
                CommandManager.INSTANCE);
    }
    
    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.saveConfig();
        this.reloadConfig();
        Configuration.loadConfig();
        this.onConfig();
        this.onListener();
        managerList.forEach(manager -> manager.onEnable(this));
        this.moduleManager = new ModuleManager();
        this.onStorage();
        this.profileManager = new ProfileManager();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new PlaceHolderAPIExpansion().register();
    }

    @Override
    public void onDisable() {
        managerList.forEach(manager -> manager.onDisable(this));
        this.moduleManager.onDisable();
        this.profileManager.onDisable();
        plugin = null;
    }

    public static Neon getPlugin(){
        return plugin;
    }

    public void onListener(){
        PluginManager pm= Bukkit.getServer().getPluginManager();

        pm.registerEvents(new EditMenu(), this);
        pm.registerEvents(new MenuListener(this), this);
    }

    public void onConfig(){
        this.tags = new Config(this, "tags");
        this.profile = new Config(this, "profile");
    }

    public Config getTagsConfig(){
        return tags;
    }

    public Config getProfileConfig(){
        return profile;
    }

    public ModuleManager getModuleManager(){
        return moduleManager;
    }

    public void onReload(){
        this.reloadConfig();
        tags.reload();
        Configuration.loadConfig();
    }

    public void onStorage(){
        switch (plugin.getConfig().getString("settings.storage.drive")){
            case "MONGO":
                final MongoDatabase mongoDatabase;

                if (plugin.getConfig().getBoolean("settings.storage.mongo.auth.enabled")) {
                    ServerAddress serverAddress = new ServerAddress(plugin.getConfig().getString("settings.storage.mongo.address"), plugin.getConfig().getInt("settings.storage.mongo.port"));
                    MongoCredential credential = MongoCredential.createCredential(plugin.getConfig().getString("settings.storage.mongo.auth.user"),
                            plugin.getConfig().getString("settings.storage.mongo.database_name"),
                            plugin.getConfig().getString("settings.storage.mongo.auth.password").toCharArray());

                    mongoDatabase = new MongoClient(serverAddress, credential, MongoClientOptions.builder().build()).getDatabase(plugin.getConfig().getString("settings.storage.mongo.database_name"));
                } else {
                    mongoDatabase = new MongoClient(plugin.getConfig().getString("settings.storage.mongo.address"), plugin.getConfig().getInt("settings.storage.mongo.port")).getDatabase(plugin.getConfig().getString("settings.storage.mongo.database_name"));
                }

                collection = mongoDatabase.getCollection("user");
                this.storageHook = new Mongo();
                break;
            case "MYSQL":
                MySQL.connect();
                MySQL.createDatabase();
                this.storageHook = new MySQL();
                break;
            default:
                this.storageHook = new YML();
                for(String player: Neon.getPlugin().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
                    UUID uuid = UUID.fromString(player);
                    String tags = Neon.getPlugin().getProfileConfig().getConfig().getString("profile." + player + ".tag");
                    Neon.getPlugin().getStorage().setTag(uuid, tags);
                }
                break;
        }
    }

    public StorageHook getStorage(){
        return storageHook;
    }
    public static void async(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(Neon.getPlugin(), callable::call);
    }

    public interface Callable {
        void call();
    }
}
