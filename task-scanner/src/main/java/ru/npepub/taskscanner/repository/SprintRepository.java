package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.Sprint;

import java.util.Collection;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class SprintRepository implements BaseRepository<Sprint, Long> {

    private final DSLContext dsl;

    public SprintRepository(DatabaseConfig databaseConfig) {
        dsl = databaseConfig.get();
    }

    @Override
    public Optional<Sprint> getById(Long id) {
        return Optional.empty();
    }

    public Optional<Sprint> findByNumber(Long sprintNum) {
        return dsl.select()
                .from("sprint")
                .where("number = ?", sprintNum)
                .fetchOptionalInto(Sprint.class);
    }

    @Override
    public Collection<Sprint> getAll() {
        return dsl.select()
                .from("sprint")
                .fetchInto(Sprint.class);
    }

    @Override
    public Sprint save(Sprint entity) {
        Long generatedId = dsl.insertInto(table("sprint"))
                .set(field("number"), entity.getNumber())
                .returningResult(field("id", Long.class))
                .fetchOptional()
                .map(record -> record.get(field("id", Long.class)))
                .orElseThrow(() -> new RuntimeException("Failed to save sprint"));

        entity.setId(generatedId);
        return entity;
    }

    @Override
    public Sprint update(Long id, Sprint entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

}
