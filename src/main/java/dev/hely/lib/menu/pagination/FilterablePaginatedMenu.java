package dev.hely.lib.menu.pagination;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.hely.lib.menu.button.Button;
import dev.hely.lib.menu.pagination.button.PageFilterButton;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public abstract class FilterablePaginatedMenu<T> extends PaginatedMenu {
    private final List<PageFilter<T>> filters;
    private int scrollIndex;

    public FilterablePaginatedMenu() {
        this.scrollIndex = 0;
        this.filters = ((this.generateFilters() == null) ? Lists.newArrayList() : this.generateFilters());
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        final Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(49, new PageFilterButton<>((FilterablePaginatedMenu<Object>) this));
        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        return this.getFilteredButtons(player);
    }

    public abstract Map<Integer, Button> getFilteredButtons(Player player);

    public abstract List<PageFilter<T>> generateFilters();
    public List<PageFilter<T>> getFilters() {
        return this.filters;
    }
    public int getScrollIndex() {
        return this.scrollIndex;
    }
    public void setScrollIndex(final int scrollIndex) {
        this.scrollIndex = scrollIndex;
    }
}