package dev.hely.tag;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "Neon";
    }

    @Override
    public String getAuthor() {
        return "ByJoako and LeandroSSJ";
    }

    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("_player_tag")){
            return Neon.getPlugin().getProfileManager().getStorage().getTag(player.getUniqueId());
        }
        return null;
    }
}
