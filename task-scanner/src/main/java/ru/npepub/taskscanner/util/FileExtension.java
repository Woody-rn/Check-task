package ru.npepub.taskscanner.util;

import java.nio.file.Path;

public enum FileExtension {

    TXT(".txt"),
    ALL(".*")
    ;

    private final String extension;

    FileExtension(String value) {
        this.extension = value;
    }

    public boolean matches(String fileName) {
        if (this == ALL) {
            return true;
        }
        return fileName.toLowerCase().endsWith(extension.toLowerCase());
    }

    public boolean matches(Path path) {
        return matches(path.getFileName().toString());
    }
}
