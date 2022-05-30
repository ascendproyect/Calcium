package dev.hely.tag.profile.storage;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import dev.hely.tag.Neon;
import dev.hely.tag.profile.StorageHook;
import org.bson.Document;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Mongo implements StorageHook {

    private final Map<UUID, String> storage = new ConcurrentHashMap<>();

    @Override
    public String getTag(UUID player) {
        if(storage.containsKey(player)) return storage.get(player);

        Document document = Neon.getPlugin().getProfileManager().getCollection().find(Filters.eq("UUID", player.toString())).first();
        if (document == null) {
            storage.put(player, "");
            return "";
        }
        storage.put(player, document.getString("Tag"));
        return document.getString("Tag");
    }

    @Override
    public void setTag(UUID player, String tag) {
        if (storage.containsKey(player)) storage.remove(player);
        storage.put(player, tag);

        Document document = new Document();
        document.put("UUID", player.toString());
        document.put("Tag", tag);
        Neon.getPlugin().getProfileManager().getCollection().replaceOne(Filters.eq("UUID", player.toString()), document, new ReplaceOptions().upsert(true));
    }
}
