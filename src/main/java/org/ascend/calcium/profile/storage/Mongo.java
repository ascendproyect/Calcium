package org.ascend.calcium.profile.storage;

import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.ascend.calcium.Calcium;
import org.ascend.calcium.profile.StorageHook;
import dev.hely.lib.CC;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Mongo implements StorageHook {

    private MongoCollection<Document> collection;
    private final Map<UUID, String> storage;
    private final Map<UUID, List<String>> favouritesStorage;

    public Mongo(){
         this.storage = new ConcurrentHashMap<>();
        this.favouritesStorage = new ConcurrentHashMap<>();
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
    public List<String> getFavourites(UUID player) {
        if(this.favouritesStorage.containsKey(player)) {
            return this.favouritesStorage.get(player);
        }

        Document document = this.collection.find(Filters.eq("UUID", player.toString())).first();
        if (document == null) {
            this.storage.put(player, "");
            return Arrays.asList("");
        }
        Type data = new TypeToken<List<UUID>>() {}.getType();
        List<String> favs = Calcium.PLAIN_GSON.fromJson(document.getString("Favourites"), data);
        favouritesStorage.put(player, favs);
        return favs;
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
    public void setFavourites(UUID player, List<String> favourites) {
        this.favouritesStorage.remove(player);
        this.favouritesStorage.put(player, favourites);

        Type data = new TypeToken<List<String>>() {}.getType();

        Document document = new Document();
        document.put("UUID", player.toString());
        document.put("Favourites", Calcium.PLAIN_GSON.toJson(favourites, data));

        this.collection.replaceOne(Filters.eq("UUID", player.toString()), document, new ReplaceOptions().upsert(true));

    }

    @Override
    public void onEnable() {
        MongoDatabase mongoDatabase;
        if (Calcium.getInstance().getConfig().getBoolean("settings.storage.mongo.auth.enabled")) {
            ServerAddress serverAddress = new ServerAddress(Calcium.getInstance().getConfig().getString("settings.storage.mongo.address"),
                    Calcium.getInstance().getConfig().getInt("settings.storage.mongo.port"));
            MongoCredential credential = MongoCredential.createCredential(Calcium.getInstance().getConfig().getString("settings.storage.mongo.auth.user"),
                    Calcium.getInstance().getConfig().getString("settings.storage.mongo.database_name"),
                    Calcium.getInstance().getConfig().getString("settings.storage.mongo.auth.password").toCharArray());

            mongoDatabase = new MongoClient(serverAddress, credential,
                    MongoClientOptions.builder().build()).getDatabase(Calcium.getInstance().getConfig().getString("settings.storage.mongo.database_name"));
        } else {
            mongoDatabase = new MongoClient(Calcium.getInstance().getConfig().getString("settings.storage.mongo.address"),
                    Calcium.getInstance().getConfig().getInt("settings.storage.mongo.port")).getDatabase(Calcium.getInstance().getConfig().getString("settings.storage.mongo.database_name"));
        }

        this.collection = mongoDatabase.getCollection("user");

        Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Calcium] &aYou have successfully connected to the &6Mongo Database&a!"));
    }

    @Override
    public void onDisable() {

    }
}
