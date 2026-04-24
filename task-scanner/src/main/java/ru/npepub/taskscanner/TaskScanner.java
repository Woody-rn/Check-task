package ru.npepub.taskscanner;

import io.javalin.Javalin;
import ru.npepub.taskscanner.config.DIContainer;

public class TaskScanner {
    public static void main(String[] args) {
        var container = new DIContainer();
        Javalin javalin = container.createJavalin();
        javalin.start(7000);
    }
}
