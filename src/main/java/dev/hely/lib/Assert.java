package dev.hely.lib;

import lombok.experimental.UtilityClass;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Wednesday, July 14, 2021
 */

@UtilityClass
public class Assert {

    public static <N> void assertNotNull(N object) {
        if (object == null) throw new NullPointerException();
    }

    @SafeVarargs
    public static <N> void assertNotNull(N... array) {
        for (N object : array) assertNotNull(object);
    }

    public static void assertPositive(byte number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(byte... array) {
        for (byte b : array) assertPositive(b);
    }

    public static void assertPositive(short number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(short... array) {
        for (short s : array) assertPositive(s);
    }

    public static void assertPositive(int number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(int... array) {
        for (int i : array) assertPositive(i);
    }

    public static void assertPositive(long number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(long... array) {
        for (long l : array) assertPositive(l);
    }

    public static void assertPositive(double number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(double... array) {
        for (double d : array) assertPositive(d);
    }

    public static void assertPositive(float number) {
        if (number < 0) throw new IllegalArgumentException();
    }

    public static void assertPositive(float... array) {
        for (float f : array) assertPositive(f);
    }
}
