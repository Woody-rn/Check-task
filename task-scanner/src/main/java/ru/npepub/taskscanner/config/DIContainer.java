package ru.npepub.taskscanner.config;

import io.javalin.Javalin;
import org.thymeleaf.TemplateEngine;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.config.web.JavalinConfigurator;
import ru.npepub.taskscanner.config.web.RouteConfigurator;
import ru.npepub.taskscanner.config.web.TemplateEngineConfigurator;
import ru.npepub.taskscanner.controller.ScanController;
import ru.npepub.taskscanner.controller.Validator;
import ru.npepub.taskscanner.exception.ExceptionHandler;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;
import ru.npepub.taskscanner.repository.SprintRepository;
import ru.npepub.taskscanner.repository.TaskRepository;
import ru.npepub.taskscanner.service.*;
import ru.npepub.taskscanner.util.FileExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DIContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private boolean initialized = false;

    public DIContainer() {
        initialize();
    }

    private void initialize() {
        if (initialized) return;

        try {
            registerDatabaseComponents();
            registerRepositoryComponents();
            registerServiceComponents();
            registerWebComponents();
            registerControllerComponents();
            registerConfiguratorComponents();
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DI container", e);
        }
    }

    private void registerConfiguratorComponents() {
        registerSingleton(RouteConfigurator.class,
                new RouteConfigurator(
                        get(ScanController.class)));
    }

    private void registerControllerComponents() {
        registerSingleton(ScanController.class,
                new ScanController(
                        get(ProcessingCoordinator.class),
                        get(TemplateEngine.class),
                        get(Validator.class)
                ));
    }

    private void registerWebComponents() {
        registerSingleton(Validator.class,
                new Validator());
        registerSingleton(TemplateEngine.class,
                TemplateEngineConfigurator.create());
        registerSingleton(ExceptionHandler.class,
                new ExceptionHandler());
    }

    private void registerServiceComponents() {
        registerSingleton(FileSearchService.class,
                new FileSearchService(
                        Set.of(FileExtension.TXT)));
        registerSingleton(PathParserService.class,
                new PathParserService());
        registerSingleton(FileInfoCollector.class,
                new FileInfoCollector(
                        get(FileSearchService.class),
                        get(PathParserService.class)
                ));

        registerSingleton(SprintService.class,
                new SprintService(
                        get(SprintRepository.class)));
        registerSingleton(TaskService.class,
                new TaskService(
                        get(TaskRepository.class)));
        registerSingleton(FileMetaDataService.class,
                new FileMetaDataService(
                        get(FileMetaDataRepository.class)));


        registerSingleton(ProcessingCoordinator.class,
                new ProcessingCoordinator(
                        get(FileInfoCollector.class),
                        get(SprintService.class),
                        get(TaskService.class),
                        get(FileMetaDataService.class)
                ));
    }

    private void registerRepositoryComponents() {
        registerSingleton(SprintRepository.class,
                new SprintRepository(
                        get(DatabaseConfig.class)));
        registerSingleton(TaskRepository.class,
                new TaskRepository(
                        get(DatabaseConfig.class)));
        registerSingleton(FileMetaDataRepository.class,
                new FileMetaDataRepository(
                        get(DatabaseConfig.class)));
    }

    private void registerDatabaseComponents() {
        registerSingleton(DatabaseConfig.class,
                new DatabaseConfig());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        Object instance = instances.get(clazz);
        if (instance == null) {
            throw new IllegalStateException("No instance registered for " + clazz.getName());
        }
        return (T) instance;
    }

    public Javalin createJavalin() {
        return JavalinConfigurator.create(
                get(ExceptionHandler.class),
                get(RouteConfigurator.class)
        );
    }

    public void shutdown() {
        DatabaseConfig dbConfig = get(DatabaseConfig.class);
        if (dbConfig != null) {
            dbConfig.close();
        }
        instances.clear();
        initialized = false;
    }

    private <T> void registerSingleton(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }
}