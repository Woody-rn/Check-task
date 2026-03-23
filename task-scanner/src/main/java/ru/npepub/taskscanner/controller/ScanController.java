package ru.npepub.taskscanner.controller;

import io.javalin.http.Context;
import org.thymeleaf.TemplateEngine;
import ru.npepub.taskscanner.dto.ScanRequest;
import ru.npepub.taskscanner.service.ProcessingCoordinator;

import java.util.HashMap;
import java.util.Map;

public class ScanController {

    private final ProcessingCoordinator processingCoordinator;
    private final TemplateEngine templateEngine;
    private final Validator validator;

    public ScanController(ProcessingCoordinator processingCoordinator,
                          TemplateEngine template,
                          Validator validator) {
        this.processingCoordinator = processingCoordinator;
        this.templateEngine = template;
        this.validator = validator;
    }

    public void scanAndUpload(Context ctx) {
        validator.validate(ctx);
        // Теперь это работает, потому что это POST запрос с телом
        ScanRequest request = ctx.bodyAsClass(ScanRequest.class);
        String path = request.getPath();
        boolean recursive = request.isRecursive();

        System.out.println("Path to scan: " + path);
        System.out.println("Recursive: " + recursive);

        // Здесь ваша логика сканирования
        processingCoordinator.scanFiles(path);

        // Отправьте JSON обратно, как ожидает JavaScript
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("path", path);
        result.put("recursive", recursive);
        // Добавьте результаты сканирования

        ctx.json(result);  // ← Отправьте JSON, а не HTML
    }

    public void get(Context ctx){
        String html = templateEngine.process("upload",
                new org.thymeleaf.context.Context(ctx.req().getLocale()));
        ctx.contentType("text/html")
                .result(html);
    }
}
