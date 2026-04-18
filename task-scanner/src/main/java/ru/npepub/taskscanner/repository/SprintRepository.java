package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.SprintEntity;

import java.util.Collection;
import java.util.Optional;

import static ru.npepub.taskscanner.jooq.generated.Tables.SPRINT;

public class SprintRepository implements BaseRepository<SprintEntity, Long> {

    private final DSLContext dsl;

    public SprintRepository(DatabaseConfig databaseConfig) {
        dsl = databaseConfig.get();
    }

    @Override
    public Optional<SprintEntity> getById(Long id) {
        return Optional.empty();
    }

    public Optional<SprintEntity> findByNumber(Integer sprintNum) {
        return dsl.selectFrom(SPRINT)
                .where(SPRINT.NUMBER.eq(sprintNum))
                .fetchOptionalInto(SprintEntity.class);
    }

    @Override
    public Collection<SprintEntity> getAll() {
        return dsl.selectFrom(SPRINT)
                .fetchInto(SprintEntity.class);
    }

    @Override
    public SprintEntity save(SprintEntity entity) {
        Long generatedId = dsl.insertInto(SPRINT)
                .set(SPRINT.NUMBER, entity.getNumber())
                .returningResult(SPRINT.ID)
                .fetchOptional()
                .map(record -> record.get(SPRINT.ID))
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
