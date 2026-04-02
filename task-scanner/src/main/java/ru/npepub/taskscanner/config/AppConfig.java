package ru.npepub.taskscanner.config;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {
    private static volatile AppConfig instance;
    private final Map<String, String> config = new HashMap<>();

    private AppConfig() {
        load();
    }

    public static AppConfig get() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    private void load() {
        // Обязательные переменные (без них приложение не запустится)
        config.put("db.url", requireEnv("DB_URL"));
        config.put("db.user", requireEnv("DB_USER"));
        config.put("db.password", requireEnv("DB_PASSWORD"));

        // Опциональные с дефолтами
        config.put("db.pool.size", getEnv("DB_POOL_SIZE", "10"));
        config.put("app.env", getEnv("APP_ENV", "development"));
        config.put("app.name", getEnv("APP_NAME", "TaskScanner"));
    }

    private String requireEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable '" + key + "' is not set"
            );
        }
        return value;
    }

    private String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    // Getters
    public String getDbUrl() { return config.get("db.url"); }
    public String getDbUser() { return config.get("db.user"); }
    public String getDbPassword() { return config.get("db.password"); }
    public int getDbPoolSize() { return Integer.parseInt(config.get("db.pool.size")); }
    public String getAppEnv() { return config.get("app.env"); }
    public boolean isProduction() { return "production".equals(getAppEnv()); }
}
