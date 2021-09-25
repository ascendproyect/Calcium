package dev.hely.hcf.lib.confirmation;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import com.google.common.collect.Maps;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.callback.MenuCallback;
import dev.hely.lib.menu.confirmation.button.ConfirmButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ConfirmMenu extends Menu {
    private final String title;
    private final MenuCallback<Boolean> response;


    public ConfirmMenu(String title, MenuCallback<Boolean> response) {
        this.title = title;
        this.response = response;

    }
    @Override
    public String getTitle(Player player) {
        return this.title;
    }
    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = Maps.newHashMap();
        for ( int i=0; i < 9; ++i ) {
            if (i == 3) {
                buttons.put(i, new ConfirmButton(true, this.response));
                continue;
            }
            if (i == 5) {
                buttons.put(i, new ConfirmButton(false, this.response));
                continue;
            }
            buttons.put(i, Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 15));
        }
        return buttons;
    }
}
