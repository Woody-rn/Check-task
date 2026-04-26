package ru.npepub.taskscanner.dto;

import java.nio.file.Path;

public record FileInfo(
        int sprintNum,
        int taskNum,
        String fileName,
        Path absolutePath,
        Path relativePath
) {
}
