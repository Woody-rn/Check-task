package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.util.FilePatternUtils;
import ru.npepub.taskscanner.util.RegexPattern;

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
            FilePatternUtils.parse(relativePath, RegexPattern.EXACT_TASK_FILE)
                    .ifPresent(info -> {
                        SprintEntity sprint = sprintService.getOrCreate(info.sprintNum());
                        TaskEntity task = taskService.getOrCreate(sprint, info.taskNum());
                        FileMetaDataEntity fileMetaData = fileMetaDataService.getOrCreate(task, relativePath);
                    });
        }
    }
}