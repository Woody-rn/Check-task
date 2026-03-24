package ru.npepub.taskscanner.config.db;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

class DatabaseMigration {

    static void run(HikariDataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            JdbcConnection jdbcConn = new JdbcConnection(conn);
            Database impDB = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(jdbcConn);

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    impDB
            );

            liquibase.update(new Contexts());
        } catch (Exception e) {
            throw new RuntimeException("Migration failed", e);
        }
    }
}
