package dev.hely.tag.profile;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.hely.lib.CC;
import dev.hely.tag.Neon;
import dev.hely.tag.profile.storage.Mongo;
import dev.hely.tag.profile.storage.MySQL;
import dev.hely.tag.profile.storage.YML;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileManager {

    private final StorageHook storage;
    private MongoCollection<Document> collection;
    private Connection connection;

    public ProfileManager (){

        Bukkit.getConsoleSender().sendMessage(CC.translate("&aLooking for a storage system..."));
        switch (Neon.getPlugin().getConfig().getString("settings.storage.drive")){
            case "MONGO":
                MongoDatabase mongoDatabase;
                if (Neon.getPlugin().getConfig().getBoolean("settings.storage.mongo.auth.enabled")) {
                    ServerAddress serverAddress = new ServerAddress(Neon.getPlugin().getConfig().getString("settings.storage.mongo.address"),
                            Neon.getPlugin().getConfig().getInt("settings.storage.mongo.port"));
                    MongoCredential credential = MongoCredential.createCredential(Neon.getPlugin().getConfig().getString("settings.storage.mongo.auth.user"),
                            Neon.getPlugin().getConfig().getString("settings.storage.mongo.database_name"),
                            Neon.getPlugin().getConfig().getString("settings.storage.mongo.auth.password").toCharArray());

                    mongoDatabase = new MongoClient(serverAddress, credential,
                            MongoClientOptions.builder().build()).getDatabase(Neon.getPlugin().getConfig().getString("settings.storage.mongo.database_name"));
                } else {
                    mongoDatabase = new MongoClient(Neon.getPlugin().getConfig().getString("settings.storage.mongo.address"),
                            Neon.getPlugin().getConfig().getInt("settings.storage.mongo.port")).getDatabase(Neon.getPlugin().getConfig().getString("settings.storage.mongo.database_name"));
                }

                collection = mongoDatabase.getCollection("user");
                storage = new Mongo();

                Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to Mongo"));
                break;
            case "MYSQL":
                String host = Neon.getPlugin().getConfig().getString("settings.storage.mysql.host");
                int port = Neon.getPlugin().getConfig().getInt("settings.storage.mysql.port");
                String user = Neon.getPlugin().getConfig().getString("settings.storage.mysql.user");
                String database = Neon.getPlugin().getConfig().getString("settings.storage.mysql.database");
                String password = Neon.getPlugin().getConfig().getString("settings.storage.mysql.password");

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&autoReconnectForPools=true&interactiveClient=true&characterEncoding=UTF-8", user, password);
                    Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to MYSQL"));
                } catch (SQLException e) {
                    Bukkit.getLogger().info("&cMySQL connection failed");
                    e.printStackTrace();
                }

                try {
                    PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Neon "+
                            "(UUID VARCHAR(100), Tag VARCHAR(100))");
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                storage = new MySQL();
                break;
            default:
                storage = new YML();
                for(String player: Neon.getPlugin().getProfileConfig().getConfig().getConfigurationSection("profile").getKeys(false)){
                    UUID uuid = UUID.fromString(player);
                    String tags = Neon.getPlugin().getProfileConfig().getConfig().getString("profile." + player + ".tag");
                    Neon.getPlugin().getProfileManager().getStorage().setTag(uuid, tags);
                }
                Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to the default YAML system"));
                break;
        }
    }

    private boolean isConnected(){
        return connection != null;
    }

    public void onDisable(){
        switch (Neon.getPlugin().getConfig().getString("settings.storage.drive")){
            case "MONGO":
                break;
            case "MYSQL":
                if(!isConnected()){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                FileConfiguration config = Neon.getPlugin().getProfileConfig().getConfig();
                for (Map.Entry<UUID, String> entry : YML.getTag().entrySet()) {
                    config.set("profile." + entry.getKey().toString() + ".tag", entry.getValue());
                }
                Neon.getPlugin().getProfileConfig().save();
                break;
        }
    }
}
