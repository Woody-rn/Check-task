package ru.npepub.taskscanner.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

class DatabaseContext {

    private static DSLContext dslContext;

    static void init(HikariDataSource dataSource) {
        dslContext = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    static DSLContext get() {
        if (dslContext == null) {
            throw new IllegalStateException("JooqConfig not initialized");
        }
        return dslContext;
    }
}
