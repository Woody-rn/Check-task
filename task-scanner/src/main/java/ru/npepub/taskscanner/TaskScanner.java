package ru.npepub.taskscanner;

import io.javalin.Javalin;
import ru.npepub.taskscanner.config.JavalinConfigurator;
import ru.npepub.taskscanner.config.RouteConfigurator;
import ru.npepub.taskscanner.config.TemplateEngineConfigurator;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.controller.ScanController;
import ru.npepub.taskscanner.controller.Validator;
import ru.npepub.taskscanner.exception.ExceptionHandler;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;
import ru.npepub.taskscanner.repository.SprintRepository;
import ru.npepub.taskscanner.repository.TaskRepository;
import ru.npepub.taskscanner.service.*;

public class TaskScanner {
    public static void main(String[] args) {
        var coordinator = init();

        var template = TemplateEngineConfigurator.create();
        var validator = new Validator();
        var scanController = new ScanController(coordinator, template, validator);

        Javalin javalin = JavalinConfigurator.create(
                new ExceptionHandler(),
                new RouteConfigurator(scanController)
        );

        javalin.start(7000);
    }

    private static ProcessingCoordinator init(){
        var databaseConfig = new DatabaseConfig();
        var sprintRepository = new SprintRepository(databaseConfig);
        var taskRepository = new TaskRepository(databaseConfig);
        var fileMetaDataRepository = new FileMetaDataRepository(databaseConfig);

        var fileSearchService = new FileSearchService();
        var pathParserService = new PathParserService();

        var sprintService = new SprintService(sprintRepository);
        var taskService = new TaskService(taskRepository);
        var fileMetaDataService = new FileMetaDataService(fileMetaDataRepository);

        return new ProcessingCoordinator(pathParserService,
                fileSearchService,
                sprintService,
                taskService,
                fileMetaDataService
                );
    }
}
