package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.dto.SprintTaskInfo;
import ru.npepub.taskscanner.entity.FileMetaDataEntity;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.util.PathTemplate;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class ProcessingCoordinator {
    private final PathParserService pathParserService;
    private final FileSearchService fileSearchService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final FileMetaDataService fileMetaDataService;

    public ProcessingCoordinator(PathParserService pathParserService,
                                 FileSearchService fileSearchService,
                                 SprintService sprintService,
                                 TaskService taskService,
                                 FileMetaDataService fileMetaDataService) {

        this.pathParserService = pathParserService;
        this.fileSearchService = fileSearchService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.fileMetaDataService = fileMetaDataService;
    }

    public void scanDirectory(String pathToDirectory) {
        List<Path> validFiles = fileSearchService.findAbsolutePaths(pathToDirectory);
        saveFilesToDatabase(validFiles);
    }

    private void saveFilesToDatabase(List<Path> paths) {
        for (Path path : paths) {
            Optional<SprintTaskInfo> infoOptional = pathParserService.parse(path, PathTemplate.SPRINT_TASK_FILE);
            if(infoOptional.isPresent()) {
                SprintTaskInfo info = infoOptional.get();

                SprintEntity sprint = sprintService.findOrSave(info.sprintNum());
                TaskEntity task = taskService.findOrSave(sprint, info.taskNum());
                FileMetaDataEntity fileMetaData = fileMetaDataService.findOrSave(task, path);
            }
        }
    }
}