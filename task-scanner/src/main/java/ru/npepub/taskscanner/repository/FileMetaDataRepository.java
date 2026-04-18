package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.FileMetaDataEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ru.npepub.taskscanner.jooq.generated.Tables.FILE_METADATA;

public class FileMetaDataRepository implements BaseRepository<FileMetaDataEntity, Long> {

    private final DSLContext dsl;

    public FileMetaDataRepository(DatabaseConfig databaseConfig) {
        dsl = databaseConfig.get();
    }

    @Override
    public Optional<FileMetaDataEntity> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<FileMetaDataEntity> getAll() {
        return List.of();
    }

    @Override
    public FileMetaDataEntity save(FileMetaDataEntity entity) {
        Long generatedId = dsl.insertInto(FILE_METADATA)
                .set(FILE_METADATA.TASK_ID, entity.getTaskId())
                .set(FILE_METADATA.SPRINT_ID, entity.getSprintId())
                .set(FILE_METADATA.FILE_NAME, entity.getFileName())
                .set(FILE_METADATA.S3_KEY, entity.getS3Key())
                .set(FILE_METADATA.S3_URL, entity.getS3Url())
                .returningResult(FILE_METADATA.ID)
                .fetchOptional()
                .map(record -> record.get(FILE_METADATA.ID))
                .orElseThrow(() -> new RuntimeException("Failed to save file_metadata"));

        entity.setId(generatedId);
        return entity;
    }

    @Override
    public FileMetaDataEntity update(Long aLong, FileMetaDataEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<FileMetaDataEntity> findByTaskIdAndFileName(Long taskId, String fileName) {
        return dsl.selectFrom(FILE_METADATA)
                .where(FILE_METADATA.TASK_ID.eq(taskId)
                        .and(FILE_METADATA.FILE_NAME.eq(fileName)))
                .fetchOptionalInto(FileMetaDataEntity.class);
    }
}
