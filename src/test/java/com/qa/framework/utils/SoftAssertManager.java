package com.qa.framework.utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertManager {
    private static final ThreadLocal<SoftAssert> INSTANCE = ThreadLocal.withInitial(SoftAssert::new);
    
    public static SoftAssert get() {
        return INSTANCE.get();
    }

    public static void assertAll() {
        try {
            INSTANCE.get().assertAll();
        } finally {
            INSTANCE.remove();
        }
    }

    private SoftAssertManager() {}
}
