package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.TaskEntity;

import java.util.Collection;
import java.util.Optional;

import static ru.npepub.taskscanner.jooq.generated.Tables.TASK;

public class TaskRepository implements BaseRepository<TaskEntity, Long> {

    private final DSLContext dsl;

    public TaskRepository(DatabaseConfig databaseConfig) {
        this.dsl = databaseConfig.get();
    }

    @Override
    public Optional<TaskEntity> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<TaskEntity> getAll() {
        return dsl.selectFrom(TASK)
                .fetchInto(TaskEntity.class);
    }

    @Override
    public TaskEntity save(TaskEntity entity) {
        Long generatedId = dsl.insertInto(TASK)
                .set(TASK.NUMBER, entity.getNumber())
                .set(TASK.SPRINT_ID, entity.getSprintId())
                .returningResult(TASK.ID)
                .fetchOptional()
                .map(record -> record.get(TASK.ID))
                .orElseThrow(() -> new RuntimeException("Failed to save task"));

        entity.setId(generatedId);
        return entity;
    }

    @Override
    public TaskEntity update(Long aLong, TaskEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<TaskEntity> findBySprintIdAndTaskNumber(Long sprintId, Integer taskNumber) {
        return dsl.selectFrom(TASK)
                .where(TASK.SPRINT_ID.eq(sprintId).and(TASK.NUMBER.eq(taskNumber)))
                .fetchOptionalInto(TaskEntity.class);
    }
}
