package dev.hely.tag.menu.edit;

import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import org.bukkit.entity.Player;

import java.util.Map;

public class CategoryMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return "&7Category edit";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        return null;
    }
}
