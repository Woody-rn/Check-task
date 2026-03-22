package ru.npepub.taskscanner;

import io.javalin.Javalin;
import ru.npepub.taskscanner.config.JavalinConfigurator;
import ru.npepub.taskscanner.config.RouteConfigurator;
import ru.npepub.taskscanner.config.TemplateEngineConfigurator;
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
        var scanController = new ScanController(coordinator, template);
        var validator = new Validator();

        Javalin javalin = JavalinConfigurator.create(
                new ExceptionHandler(),
                new RouteConfigurator(scanController, validator)
        );
        javalin.start(7000);
    }

    private static ProcessingCoordinator init(){
        var fileSearchService = new FileSearchService();
        var sprintService = new SprintService(new SprintRepository());
        var taskService = new TaskService(new TaskRepository());
        var fileMetaDataService = new FileMetaDataService(new FileMetaDataRepository());

        return new ProcessingCoordinator(fileSearchService,
                sprintService,
                taskService,
                fileMetaDataService
                );
    }
}
