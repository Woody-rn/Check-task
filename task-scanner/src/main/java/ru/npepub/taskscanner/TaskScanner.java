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
import ru.npepub.taskscanner.service.UploaderService;

public class TaskScanner {
    public static void main(String[] args) {
        var sprintRepository = new SprintRepository();
        var taskRepository = new TaskRepository();
        var fileMetaDataRepository = new FileMetaDataRepository();
        var uploader = new UploaderService(sprintRepository, taskRepository,fileMetaDataRepository);


        var template = TemplateEngineConfigurator.create();
        var scanController = new ScanController(uploader, template);
        var validator = new Validator();

        Javalin javalin = JavalinConfigurator.create(
                new ExceptionHandler(),
                new RouteConfigurator(scanController, validator)
        );
        javalin.start(7000);
    }
}
