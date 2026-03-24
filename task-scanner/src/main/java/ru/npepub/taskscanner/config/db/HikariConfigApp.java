package ru.npepub.taskscanner.config.db;

import com.zaxxer.hikari.HikariDataSource;

class HikariConfigApp {

    private static HikariDataSource dataSource;

    static HikariDataSource createDataSource() {
        com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/taskdb");
        config.setUsername("sa");
        config.setPassword("");
        config.setMaximumPoolSize(5);

        dataSource = new HikariDataSource(config);

        return dataSource;
    }

    static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
