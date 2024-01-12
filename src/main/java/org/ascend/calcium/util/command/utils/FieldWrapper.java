package org.ascend.calcium.util.command.utils;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: net.andrew.prisons.utils.command.utils
//   Date: Sunday, June 25, 2023 - 5:30 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import lombok.NonNull;

import java.lang.reflect.Field;

public class FieldWrapper {

    @NonNull private final Field field;
    @NonNull
    private final ClassWrapper wrapper;

    public FieldWrapper(@NonNull ClassWrapper wrapper, @NonNull String name) throws NoSuchFieldException {
        this.wrapper = wrapper;
        this.field = wrapper.getInstanceClass().getDeclaredField(name);
    }

    public Object get() {
        field.setAccessible(true);

        try {
            return field.get(wrapper.getInstance());
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
