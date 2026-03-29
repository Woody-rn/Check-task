package ru.npepub.taskscanner.repository;

import org.jooq.DSLContext;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.FileMetaData;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        System.out.println("\t\tFile - " + entity.getS3Url());
        return null;
    }

    @Override
    public FileMetaData update(Long aLong, FileMetaData entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<FileMetaData> findByTaskIdAndFileName(Long id, String fileName) {
        return Optional.empty();
    }
}
