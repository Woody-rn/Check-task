package ru.npepub.taskscanner.controller;

import io.javalin.http.Context;
import ru.npepub.taskscanner.dto.ScanRequest;
import ru.npepub.taskscanner.exception.ApiException;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    public void validate(Context ctx) {
        String path = ctx.bodyAsClass(ScanRequest.class).getPath();
        validatePathExists(path);
        validatePathNotBlank(path);
        validateFileAccessible(path);
    }

    private static void validatePathExists(String path) {
        if (path == null || path.isEmpty()) {
            throw new ApiException(400, "Path parameter is required");
        }
    }

    private static void validatePathNotBlank(String path) {
        if (path.isBlank()) {
            throw new ApiException(400, "Path parameter is required");
        }
    }

    private static void validateFileAccessible(String path) {
        if (!Files.exists(Path.of(path))) {
            throw new ApiException(400, "File does not exist: " + path);
        }
    }
}
