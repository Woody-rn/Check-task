package ru.npepub.taskscanner.repository;

import ru.npepub.taskscanner.entity.FileMetaData;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FileMetaDataRepository implements BaseRepository<FileMetaData, Long>{
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
        System.out.println("File - " + entity.getS3Url());
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
}
