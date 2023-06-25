package dev.hely.tag.profile.storage;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import dev.hely.lib.CC;
import dev.hely.tag.Neon;
import dev.hely.tag.profile.StorageHook;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Mongo implements StorageHook {

    private MongoCollection<Document> collection;
    private final Map<UUID, String> storage;

    public Mongo(){
         this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public String getTag(UUID player) {
        if(this.storage.containsKey(player)) return this.storage.get(player);

        Document document = this.collection.find(Filters.eq("UUID", player.toString())).first();
        if (document == null) {
            this.storage.put(player, "");
            return "";
        }
        storage.put(player, document.getString("Tag"));
        return document.getString("Tag");
    }

    @Override
    public void setTag(UUID player, String tag) {
        this.storage.remove(player);
        this.storage.put(player, tag);

        Document document = new Document();
        document.put("UUID", player.toString());
        document.put("Tag", tag);
        this.collection.replaceOne(Filters.eq("UUID", player.toString()), document, new ReplaceOptions().upsert(true));
    }

    @Override
    public void onEnable() {
        MongoDatabase mongoDatabase;
        if (Neon.getInstance().getConfig().getBoolean("settings.storage.mongo.auth.enabled")) {
            ServerAddress serverAddress = new ServerAddress(Neon.getInstance().getConfig().getString("settings.storage.mongo.address"),
                    Neon.getInstance().getConfig().getInt("settings.storage.mongo.port"));
            MongoCredential credential = MongoCredential.createCredential(Neon.getInstance().getConfig().getString("settings.storage.mongo.auth.user"),
                    Neon.getInstance().getConfig().getString("settings.storage.mongo.database_name"),
                    Neon.getInstance().getConfig().getString("settings.storage.mongo.auth.password").toCharArray());

            mongoDatabase = new MongoClient(serverAddress, credential,
                    MongoClientOptions.builder().build()).getDatabase(Neon.getInstance().getConfig().getString("settings.storage.mongo.database_name"));
        } else {
            mongoDatabase = new MongoClient(Neon.getInstance().getConfig().getString("settings.storage.mongo.address"),
                    Neon.getInstance().getConfig().getInt("settings.storage.mongo.port")).getDatabase(Neon.getInstance().getConfig().getString("settings.storage.mongo.database_name"));
        }

        this.collection = mongoDatabase.getCollection("user");

        Bukkit.getConsoleSender().sendMessage(CC.translate("&aYou have successfully connected to Mongo"));
    }

    @Override
    public void onDisable() {

    }
}
