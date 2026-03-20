package ru.npepub.taskscanner.exception;

import io.javalin.Javalin;
import ru.npepub.taskscanner.dto.ErrorResponse;

public class ExceptionHandler {
    public void register(Javalin app) {
        app.exception(ApiException.class, (e, ctx) -> {
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json(new ErrorResponse("Internal server error"));
            e.printStackTrace();
        });
    }
}
