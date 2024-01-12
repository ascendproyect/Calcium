package org.ascend.calcium.util.command.utils;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: net.andrew.prisons.utils.command
//   Date: Sunday, June 25, 2023 - 5:30 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

import lombok.Getter;
import lombok.NonNull;

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
