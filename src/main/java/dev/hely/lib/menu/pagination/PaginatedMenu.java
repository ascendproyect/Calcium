package dev.hely.lib.menu.pagination;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import com.google.common.collect.Maps;
import dev.hely.lib.menu.Menu;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.button.PageButton;
import dev.hely.lib.menu.pagination.button.PageInfoButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class PaginatedMenu extends Menu {
    private int page;
    private boolean infoButton;

    public PaginatedMenu() {
        this.page = 1;
        this.infoButton = true;
        this.setUpdateAfterClick(false);
    }

    @Override
    public String getTitle(Player player) {
        return this.getPrePaginatedTitle(player) +
                (this.infoButton ? "" : (" [" + this.getPage() + "/" + this.getPages(player) + "]"));
    }

    public final void modPage(Player player, int mod) {
        this.page += mod;
        this.getButtons().clear();
        this.openMenu(player);
    }

    public final int getPages(Player player) {
        int buttonAmount = this.getAllPagesButtons(player).size();
        if (buttonAmount == 0) {
            return 1;
        }
        return (int) Math.ceil(buttonAmount / (double) this.getMaxItemsPerPage(player));
    }

    @Override
    public final Map<Integer, Button> getButtons(Player player) {
        int minIndex = (int) ((this.page - 1) * (double) this.getMaxItemsPerPage(player));
        int maxIndex = (int) (this.page * (double) this.getMaxItemsPerPage(player));
        HashMap<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < 8; ++i) {
            buttons.put(i, this.getPlaceholderButton());
        }
        buttons.put(0, new PageButton(-1, this));
        if (this.infoButton) {
            buttons.put(4, new PageInfoButton(this));
        }
        buttons.put(8, new PageButton(1, this));

        for (int i = 45; i < 54; ++i) {
            buttons.put(i, this.getPlaceholderButton());
        }
        for (Map.Entry<Integer, Button> entry : this.getAllPagesButtons(player).entrySet()) {
            int ind = entry.getKey();
            if (ind >= minIndex && ind < maxIndex) {
                ind -= (int) (this.getMaxItemsPerPage(player) * (double) (this.page - 1)) - 9;
                buttons.put(ind, entry.getValue());
            }
        }
        Map<Integer, Button> global = this.getGlobalButtons(player);
        if (global != null) {
            for (Map.Entry<Integer, Button> gent : global.entrySet()) {
                buttons.put(gent.getKey(), gent.getValue());
            }
        }
        return buttons;
    }

    protected int getMaxItemsPerPage(Player player) {
        return 36;
    }

    public Map<Integer, Button> getGlobalButtons(Player player) {
        return null;
    }

    public abstract String getPrePaginatedTitle(Player player);

    public abstract Map<Integer, Button> getAllPagesButtons(Player player);

    public int getPage() {
        return this.page;
    }

    public boolean isInfoButton() {
        return this.infoButton;
    }

    public void setInfoButton(boolean infoButton) {
        this.infoButton = infoButton;
    }
}
