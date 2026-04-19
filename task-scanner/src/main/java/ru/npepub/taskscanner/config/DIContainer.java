package ru.npepub.taskscanner.config;

import org.thymeleaf.TemplateEngine;
import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.controller.ScanController;
import ru.npepub.taskscanner.controller.Validator;
import ru.npepub.taskscanner.exception.ExceptionHandler;
import ru.npepub.taskscanner.repository.*;
import ru.npepub.taskscanner.service.*;
import io.javalin.Javalin;
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

        // Конфигурация БД (синглтон)
        registerSingleton(DatabaseConfig.class, new DatabaseConfig());

        // Репозитории
        registerSingleton(SprintRepository.class,
                new SprintRepository(get(DatabaseConfig.class)));
        registerSingleton(TaskRepository.class,
                new TaskRepository(get(DatabaseConfig.class)));
        registerSingleton(FileMetaDataRepository.class,
                new FileMetaDataRepository(get(DatabaseConfig.class)));

        // Сервисы
        registerSingleton(FileSearchService.class,
                new FileSearchService(Set.of(FileExtension.TXT)));
        registerSingleton(SprintService.class,
                new SprintService(get(SprintRepository.class)));
        registerSingleton(TaskService.class,
                new TaskService(get(TaskRepository.class)));
        registerSingleton(FileMetaDataService.class,
                new FileMetaDataService(get(FileMetaDataRepository.class)));

        // Координатор
        registerSingleton(ProcessingCoordinator.class,
                new ProcessingCoordinator(
                        get(PathParserService.class),
                        get(FileSearchService.class),
                        get(SprintService.class),
                        get(TaskService.class),
                        get(FileMetaDataService.class)
                ));

        // Веб-слой
        registerSingleton(Validator.class, new Validator());
        registerSingleton(TemplateEngine.class, TemplateEngineConfigurator.create());

        // Контроллер
        registerSingleton(ScanController.class, new ScanController(
                get(ProcessingCoordinator.class),
                get(TemplateEngine.class),
                get(Validator.class)
        ));

        // Конфигураторы
        registerSingleton(RouteConfigurator.class,
                new RouteConfigurator(get(ScanController.class)));
        registerSingleton(ExceptionHandler.class, new ExceptionHandler());

        initialized = true;
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