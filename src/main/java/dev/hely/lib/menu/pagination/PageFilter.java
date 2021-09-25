package dev.hely.lib.menu.pagination;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import java.util.function.Predicate;

public class PageFilter<T> {
    private final Predicate<T> predicate;
    private String name;
    private boolean enabled;

    public PageFilter(String name, Predicate<T> predicate, boolean enabled) {
        this.name = name;
        this.predicate = predicate;
        this.enabled = enabled;
    }

    public PageFilter(Predicate<T> predicate) {
        this.predicate = predicate;
    }
    public boolean test(final T t) {
        return this.enabled && this.predicate.test(t);
    }
    public String getName() {
        return this.name;
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}