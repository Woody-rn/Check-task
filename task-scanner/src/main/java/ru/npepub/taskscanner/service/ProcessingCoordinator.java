package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.dto.FileInfo;
import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;

import java.nio.file.Path;
import java.util.List;

public class ProcessingCoordinator {

    private final FileInfoCollector collector;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final FileMetaDataService fileMetaDataService;

    public ProcessingCoordinator(FileInfoCollector collector,
                                 SprintService sprintService,
                                 TaskService taskService,
                                 FileMetaDataService fileMetaDataService) {
        this.collector = collector;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.fileMetaDataService = fileMetaDataService;
    }

    public void scanDirectory(String pathToDirectory) {

        List<FileInfo> validFiles = collector.collect(pathToDirectory);
        validFiles.forEach(this::saveFilesToDatabase);
    }

    private void saveFilesToDatabase(FileInfo fileInfo) {
        SprintEntity sprint = sprintService.findOrSave(fileInfo.sprintNum());
        TaskEntity task = taskService.findOrSave(sprint, fileInfo.taskNum());
        FileMetaDataEntity fileMetaData = fileMetaDataService.findOrSave(task, fileInfo);
    }
}
