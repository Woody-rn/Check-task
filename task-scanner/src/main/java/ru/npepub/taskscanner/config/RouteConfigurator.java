package ru.npepub.taskscanner.config;

import io.javalin.Javalin;
import ru.npepub.taskscanner.controller.ScanController;

public class RouteConfigurator {
    private final ScanController scanController;

    public RouteConfigurator(ScanController scanController) {
        this.scanController = scanController;
    }

    public void configureRoutes(Javalin app) {

        app.get("/", scanController::get);

        app.post("/scan", ctx -> {
            scanController.scanAndUpload(ctx);

        });

       /* // API endpoint
        app.post("/api/v1/upload-files", ctx -> {
            validator.validate(ctx);
            scanController.scanAndUpload(ctx);

            if (ctx.contentType().contains("application/json")) {
                ctx.status(200).json(new SuccessResponse("Upload successful"));
            } else {
                ctx.redirect("/success");
            }
        });*/
    }
}