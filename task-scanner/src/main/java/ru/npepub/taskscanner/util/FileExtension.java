package ru.npepub.taskscanner.util;

import lombok.Getter;

@Getter
public enum FileExtension {

    TXT(".*\\.txt$"),
    ALL(".*");

    private final String extension;

    FileExtension(String value) {
        this.extension = value;
    }
}
