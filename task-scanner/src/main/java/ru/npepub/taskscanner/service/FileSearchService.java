package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.util.FileExtension;
import ru.npepub.taskscanner.util.FilePatternUtils;
import ru.npepub.taskscanner.util.PathTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FileSearchService {

    private final Set<FileExtension> allowedExtensions;

    public FileSearchService(Set<FileExtension> allowedExtensions) {
        this.allowedExtensions = allowedExtensions.isEmpty()
                ? Set.of(FileExtension.TXT)
                : allowedExtensions;
    }

    List<Path> findAbsolutePaths(Path pathToDirectory) {
        try (Stream<Path> stream = Files.walk(pathToDirectory)) {
            return stream.filter(Files::isRegularFile)
                    .filter(this::hasAllowedExtension)
                    .filter(this::matchesSprintTaskPath)
                    .map(Path::toAbsolutePath)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasAllowedExtension(Path path) {
        return allowedExtensions.stream()
                .anyMatch(ext -> FilePatternUtils.matches(path, ext.getExtension()));
    }

    private boolean matchesSprintTaskPath(Path path) {
        return FilePatternUtils.matches(path, PathTemplate.SPRINT_TASK);
    }
}
