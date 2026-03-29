package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;

import java.nio.file.Path;

public class FileMetaDataService {
    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public FileMetaDataEntity getOrCreate(TaskEntity task, Path relativePath) {
        String fileName = relativePath.getFileName().toString();

        return fileMetaDataRepository
                .findByTaskIdAndFileName(task.getId(), fileName)
                .orElseGet(() -> {
                    FileMetaDataEntity fileMetaData = FileMetaDataEntity.builder()
                            .taskId(task.getId())
                            .sprintId(task.getSprintId())
                            .fileName(fileName)
                            .s3Key("s3Key")
                            .s3Url(relativePath.toString())
                            .build();

                    return fileMetaDataRepository.save(fileMetaData);
                });
    }
}
