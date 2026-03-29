package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.FileMetaData;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class FileMetaDataRepository implements BaseRepository<FileMetaData, Long> {

    private final DSLContext dsl;

    public FileMetaDataRepository(DatabaseConfig databaseConfig) {
        dsl = databaseConfig.get();
    }

    @Override
    public Optional<FileMetaData> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<FileMetaData> getAll() {
        return List.of();
    }

    @Override
    public FileMetaData save(FileMetaData entity) {
        Long generatedId = dsl.insertInto(table("file_metadata"))
                .set(field("task_id"), entity.getTaskId())
                .set(field("sprint_id"), entity.getSprintId())
                .set(field("file_name"), entity.getFileName())
                .set(field("s3_key"), entity.getS3Key())
                .set(field("s3_url"), entity.getS3Url())
                .returningResult(field("id", Long.class))
                .fetchOptional()
                .map(record -> record.get(field("id", Long.class)))
                .orElseThrow(() -> new RuntimeException("Failed to save file_metadata"));

        entity.setId(generatedId);
        return entity;
    }

    @Override
    public FileMetaData update(Long aLong, FileMetaData entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<FileMetaData> findByTaskIdAndFileName(Long taskId, String fileName) {
        return dsl.select()
                .from("file_metadata")
                .where("task_id = ? AND file_name = ?", taskId, fileName)
                .fetchOptionalInto(FileMetaData.class);
    }
}
