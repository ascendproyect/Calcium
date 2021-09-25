package dev.hely.lib;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static dev.hely.lib.Assert.assertNotNull;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Wednesday, July 14, 2021
 */

@UtilityClass
public class Reflection {

    @SuppressWarnings("unchecked")
    public static <H> Accessor<H> getFieldAccessor(Object object, String name, Class<H> type) {
        assertNotNull(object, name, type);

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals(name) && field.getType().isAssignableFrom(type)) {
                field.setAccessible(true);

                return new Accessor<H>() {

                    public H get() {
                        try {
                            return (H) field.get(object);
                        } catch (IllegalAccessException exception) {
                            throw new RuntimeException("Cannot access Reflection.");
                        }
                    }

                    public void set(H value) {
                        assertNotNull(value);

                        try {
                            field.set(object, value);
                        } catch (IllegalAccessException exception) {
                            throw new RuntimeException("Cannot access Reflection.");
                        }
                    }

                    @Override
                    public void add(Collection<H> collection) {
                        assertNotNull(collection);

                        if (type.isAssignableFrom(Collection.class)) {
                            try {
                                ((List<H>) field.get(object)).addAll(collection);
                            } catch (IllegalAccessException exception) {
                                throw new RuntimeException("Cannot access Reflection.");
                            }
                        } else {
                            throw new IllegalArgumentException("Cannot take that field as Collection.");
                        }
                    }
                };
            }
        }

        throw new IllegalArgumentException("Cannot find a field with that name and type.");
    }
}
