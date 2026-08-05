package com.qa.framework.utils;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private final Map<String, Object> store = new HashMap<>();

    public void set(String key, Object value) { 
        store.put(key, value); 
    }

    public Object get(String key) { 
        return store.get(key); 
    }

    public <T> T get(String key, Class<T> type) { 
        return type.cast(store.get(key)); 
    }

    public boolean has(String key) { 
        return store.containsKey(key); 
    }
}
