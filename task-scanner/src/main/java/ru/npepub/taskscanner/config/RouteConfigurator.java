package ru.npepub.taskscanner.config;

import io.javalin.Javalin;
import ru.npepub.taskscanner.controller.ScanController;
import ru.npepub.taskscanner.controller.Validator;
import ru.npepub.taskscanner.dto.SuccessResponse;

public class RouteConfigurator {
    private final ScanController scanController;
    private final Validator validator;

    public RouteConfigurator(ScanController scanController, Validator validator) {
        this.scanController = scanController;
        this.validator = validator;
    }

    public void configureRoutes(Javalin app) {

        app.get("/", scanController::get);

        app.post("/scan", ctx -> {
            validator.validate(ctx);
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