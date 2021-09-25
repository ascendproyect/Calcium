package dev.hely.lib.menu.callback;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, abril 11, 2021
 */

import java.io.Serializable;

public interface MenuCallback<T> extends Serializable {
    void callback(T data);
}
