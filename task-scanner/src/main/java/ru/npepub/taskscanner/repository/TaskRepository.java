package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.TaskEntity;

import java.util.Collection;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

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
        return dsl.select()
                .from("task")
                .fetchInto(TaskEntity.class);
    }

    @Override
    public TaskEntity save(TaskEntity entity) {
        Long generatedId = dsl.insertInto(table("task"))
                .set(field("number"), entity.getNumber())
                .set(field("sprint_id"), entity.getSprintId())
                .returningResult(field("id", Long.class))
                .fetchOptional()
                .map(record -> record.get(field("id", Long.class)))
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

    public Optional<TaskEntity> findBySprintIdAndTaskNumber(Long sprintId, Long taskNumber) {
        return dsl.select(
                        field("id"),
                        field("sprint_id").as("sprintId"),
                        field("number"),
                        field("created_at").as("createdAt")
                )
                .from("task")
                .where("sprint_id = ? AND number = ?", sprintId, taskNumber)
                .fetchOptionalInto(TaskEntity.class);
    }
}
