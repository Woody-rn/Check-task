package ru.npepub.taskscanner.service;

import lombok.extern.slf4j.Slf4j;
import ru.npepub.taskscanner.dto.FileInfo;
import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;

@Slf4j
public class FileMetaDataService {
    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    FileMetaDataEntity findOrSave(TaskEntity task, FileInfo fileInfo) {

        log.debug("Requesting file metadata: taskId={}, fileName={}", task.getId(), fileInfo.fileName());

        return fileMetaDataRepository
                .findByTaskIdAndFileName(task.getId(), fileInfo.fileName())
                .map(this::logFound)
                .orElseGet(() -> createAndSave(task, fileInfo));
    }

    private FileMetaDataEntity createAndSave(TaskEntity task, FileInfo fileInfo) {
        log.debug("File metadata not found, creating new: taskId={}, fileName={}",
                task.getId(), fileInfo.fileName());

        FileMetaDataEntity fileMetaData = FileMetaDataEntity.builder()
                .taskId(task.getId())
                .sprintId(task.getSprintId())
                .fileName(fileInfo.fileName())
                .s3Key("s3Key")
                .s3Url(fileInfo.relativePath().toString())
                .build();

        FileMetaDataEntity saved = fileMetaDataRepository.save(fileMetaData);
        log.debug("File metadata created: id={}, taskId={}, fileName={}, s3Url={}",
                saved.getId(), saved.getTaskId(), saved.getFileName(), saved.getS3Url());
        return saved;
    }

    private FileMetaDataEntity logFound(FileMetaDataEntity fileMetaData) {
        log.debug("File metadata found: id={}, taskId={}, fileName={}",
                fileMetaData.getId(), fileMetaData.getTaskId(), fileMetaData.getFileName());
        return fileMetaData;
    }
}