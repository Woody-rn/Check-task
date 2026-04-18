package ru.npepub.taskscanner.service;

import lombok.extern.slf4j.Slf4j;
import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;

import java.nio.file.Path;

@Slf4j
public class FileMetaDataService {
    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public FileMetaDataEntity getOrCreate(TaskEntity task, Path relativePath) {
        String fileName = relativePath.getFileName().toString();

        log.debug("Requesting file metadata: taskId={}, fileName={}", task.getId(), fileName);

        return fileMetaDataRepository
                .findByTaskIdAndFileName(task.getId(), fileName)
                .map(fileMetaData -> {
                    log.debug("File metadata found: id={}, taskId={}, fileName={}",
                            fileMetaData.getId(), fileMetaData.getTaskId(), fileMetaData.getFileName());
                    return fileMetaData;
                })
                .orElseGet(() -> {
                    log.debug("File metadata not found, creating new: taskId={}, fileName={}",
                            task.getId(), fileName);

                    FileMetaDataEntity fileMetaData = FileMetaDataEntity.builder()
                            .taskId(task.getId())
                            .sprintId(task.getSprintId())
                            .fileName(fileName)
                            .s3Key("s3Key")
                            .s3Url(relativePath.toString())
                            .build();

                    FileMetaDataEntity saved = fileMetaDataRepository.save(fileMetaData);
                    log.debug("File metadata created: id={}, taskId={}, fileName={}, s3Url={}",
                            saved.getId(), saved.getTaskId(), saved.getFileName(), saved.getS3Url());
                    return saved;
                });
    }
}