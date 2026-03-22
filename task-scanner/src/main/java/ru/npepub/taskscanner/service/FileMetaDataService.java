package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaData;
import ru.npepub.taskscanner.entity.Task;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;

import java.nio.file.Path;

public class FileMetaDataService {
    private final FileMetaDataRepository fileMetaDataRepository;

    public FileMetaDataService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public FileMetaData getOrCreate(Task task, Path relativePath) {
        String fileName = relativePath.getFileName().toString();

        return fileMetaDataRepository
                .findByTaskIdAndFileName(task.getId(), fileName)
                .orElseGet(() -> {
                    FileMetaData fileMetaData = new FileMetaData();
                    fileMetaData.setTaskId(task.getId());
                    fileMetaData.setSprintId(task.getSprintId());
                    fileMetaData.setFileName(fileName);
                    fileMetaData.setS3Key("s3Key");
                    fileMetaData.setS3Url(relativePath.toString());

                    return fileMetaDataRepository.save(fileMetaData);
                });
    }
}
