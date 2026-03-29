package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.util.FilePatternUtils;

import java.nio.file.Path;
import java.util.List;

public class ProcessingCoordinator {
    private final FileSearchService fileSearchService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final FileMetaDataService fileMetaDataService;

    public ProcessingCoordinator(FileSearchService fileSearchService,
                                 SprintService sprintService,
                                 TaskService taskService,
                                 FileMetaDataService fileMetaDataService) {
        this.fileSearchService = fileSearchService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.fileMetaDataService = fileMetaDataService;
    }

    public void scanFiles(String pathToDirectory) {
        List<Path> validFiles = fileSearchService.searchFiles(pathToDirectory);
        saveFilesToDatabase(validFiles);
    }

    private void saveFilesToDatabase(List<Path> relativePaths) {
        for (Path relativePath : relativePaths) {
            System.out.println(relativePath.toString());
            FilePatternUtils.parse(relativePath)
                    .ifPresent(info -> {
                        SprintEntity sprint = sprintService.getOrCreate(info.sprintNum());
                        System.out.println(sprint.toString());
                        TaskEntity task = taskService.getOrCreate(sprint, info.taskNum());
                        System.out.println(task.toString());
                        FileMetaDataEntity fileMetaData = fileMetaDataService.getOrCreate(task, relativePath);
                        System.out.println(fileMetaData.toString());
                    });
        }
    }
}