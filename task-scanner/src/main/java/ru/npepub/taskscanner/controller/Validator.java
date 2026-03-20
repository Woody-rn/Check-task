package ru.npepub.taskscanner.controller;

import io.javalin.http.Context;
import ru.npepub.taskscanner.dto.ScanRequest;
import ru.npepub.taskscanner.exception.ApiException;

public class Validator {

    public void validate(Context ctx) {
        String path = ctx.bodyAsClass(ScanRequest.class).getPath();
        if (path == null || path.isEmpty()) {
            throw new ApiException(400, "Path parameter is required");
        }
    }
}
