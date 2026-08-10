package com.qa.framework.pages.elements;

import java.lang.reflect.Field;

public class UtilLocator {
    public Field field(String name) {
        try {
            return this.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field '" + name + "' not found", e);
        }
    }
}
