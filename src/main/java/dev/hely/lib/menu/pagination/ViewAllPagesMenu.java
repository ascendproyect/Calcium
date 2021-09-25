package dev.hely.lib.menu.pagination;

import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.button.impl.BackButton;
import dev.hely.lib.menu.pagination.button.JumpToPageButton;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */
public class ViewAllPagesMenu extends Menu {
    @NonNull
    public PaginatedMenu menu;

    public ViewAllPagesMenu(@NonNull PaginatedMenu menu) {
        if (menu == null) {
            throw new NullPointerException("menu is marked non-null but is null");
        }
        this.menu = menu;
    }
    @Override
    public String getTitle(Player player) {
        return "Jump to page";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(0, new BackButton(this.menu));
        int index = 10;
        for (int i = 1; i <= this.menu.getPages(player); ++i) {
            buttons.put(index++, new JumpToPageButton(i, this.menu, this.menu.getPage() == i));
            if ((index - 8) % 9 == 0) {
                index += 2;
            }
        }
        return buttons;
    }
    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @NonNull
    public PaginatedMenu getMenu() {
        return this.menu;
    }
}