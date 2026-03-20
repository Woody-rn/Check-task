package ru.npepub.taskscanner.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateEngineConfigurator {

    public static TemplateEngine create() {
        TemplateEngine engine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        engine.setTemplateResolver(resolver);
        return engine;
    }
}
