package dev.hely.tag;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "neon";
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
        if(identifier.equalsIgnoreCase("player_tag")){
            return Neon.getInstance().getProfileManager().getStorage().getTag(player.getUniqueId());
        }
        return null;
    }
}
