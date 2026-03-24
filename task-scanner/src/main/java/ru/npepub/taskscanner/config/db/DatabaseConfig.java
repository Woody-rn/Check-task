package ru.npepub.taskscanner.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;

public class DatabaseConfig {

    public DatabaseConfig() {
        init();
    }

    private void init() {
        HikariDataSource dataSource = HikariConfigApp.createDataSource();
        DatabaseMigration.run(dataSource);
        DatabaseContext.init(dataSource);
    }

    public DSLContext get() {
        return DatabaseContext.get();
    }

    public void close() {
        HikariConfigApp.close();
    }
}
