package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaData;
import ru.npepub.taskscanner.entity.Sprint;
import ru.npepub.taskscanner.entity.Task;
import ru.npepub.taskscanner.util.FilePatternUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

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
                        Sprint sprint = sprintService.getOrCreate(info.sprintNum());
                        Task task = taskService.getOrCreate(sprint, info.taskNum());
                        FileMetaData fileMetaData = fileMetaDataService.getOrCreate(task, relativePath);
                    });
        }
    }
}