package dev.hely.lib.wrapper;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Tuesday, June 01, 2021
 */

@Getter
public class ClassWrapper {

    @NonNull private final Object instance;
    @NonNull private final Class<?> instanceClass;

    public ClassWrapper(@NonNull Object instance) {
        this.instance = instance;
        this.instanceClass = instance.getClass();
    }

    public FieldWrapper getField(String name) {
        try {
            return new FieldWrapper(this, name);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
