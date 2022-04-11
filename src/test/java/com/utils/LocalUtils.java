package com.utils;

import com.browserstack.local.Local;

import java.util.HashMap;
import java.util.Map;

public final class LocalUtils {

    private static Local local;
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");

    private LocalUtils() {}

    public static void startLocal() {
        local = new Local();
        Map<String, String> options = new HashMap<>();
        options.put("key", ACCESS_KEY);
        try {
            local.start(options);
        } catch (Exception e) {
            throw new RuntimeException("Unable to start a local connection", e);
        }
        System.out.println("Local testing connection established");
    }

    public static void stopLocal() {
        try {
            local.stop();
        } catch (Exception e) {
            throw new RuntimeException("Unable to stop the local connection", e);
        }
        System.out.println("Local testing connection terminated");
    }

}
