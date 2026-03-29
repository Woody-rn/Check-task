package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.SprintEntity;

import java.util.Collection;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class SprintRepository implements BaseRepository<SprintEntity, Long> {

    private final DSLContext dsl;

    public SprintRepository(DatabaseConfig databaseConfig) {
        dsl = databaseConfig.get();
    }

    @Override
    public Optional<SprintEntity> getById(Long id) {
        return Optional.empty();
    }

    public Optional<SprintEntity> findByNumber(Long sprintNum) {
        return dsl.select()
                .from("sprint")
                .where("number = ?", sprintNum)
                .fetchOptionalInto(SprintEntity.class);
    }

    @Override
    public Collection<SprintEntity> getAll() {
        return dsl.selectFrom("sprint")
                .fetchInto(SprintEntity.class);
    }

    @Override
    public SprintEntity save(SprintEntity entity) {
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
    public SprintEntity update(Long id, SprintEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

}
