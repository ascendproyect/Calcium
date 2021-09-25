package dev.hely.lib.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static dev.hely.lib.util.VersionUtil.VERSION;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Wednesday, July 21, 2021
 */

@UtilityClass
public class ServerUtil {

    private static Class<?> aClass;
    private static Object aObject;
    private static Field aField;

    static {
        try {
            aClass = Class.forName("net.minecraft.server." + VERSION + ".MinecraftServer");
            aObject = aClass.getMethod("getServer").invoke(null);
            aField = aClass.getField("recentTps");
            aField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    public static double[] getTPS() {
        if (aClass == null) {
            try {
                aClass = Class.forName("net.minecraft.server." + VERSION + ".MinecraftServer");
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        if (aObject == null) {
            try {
                aObject = aClass.getMethod("getServer").invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }

        if (aField == null) {
            try {
                aField = aClass.getField("recentTps");
                aField.setAccessible(true);
            } catch (NoSuchFieldException exception) {
                exception.printStackTrace();
            }
        }

        try {
            return (double[]) aField.get(aObject);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return new double[] { -1, -1, -1 };
    }
}
