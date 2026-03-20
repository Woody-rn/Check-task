package ru.npepub.taskscanner.config;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import ru.npepub.taskscanner.exception.ExceptionHandler;

public class JavalinConfigurator {

    public static Javalin create(ExceptionHandler exceptionHandler, RouteConfigurator routeConfigurator) {
        Javalin javalin = createConfiguredJavalin();
        exceptionHandler.register(javalin);
        routeConfigurator.configureRoutes(javalin);
        return javalin;
    }

    private static Javalin createConfiguredJavalin() {
        return Javalin.create(
                config -> {
                    // Настройка статики из папки src/main/resources/public
                    config.staticFiles.add("/public", Location.CLASSPATH);
                }
        );
    }

}
