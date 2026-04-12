package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.util.FilePatternUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileSearchService {

    public List<Path> searchFiles(String pathToDirectory) {
        Path startPath = Path.of(pathToDirectory);
        try (Stream<Path> stream = Files.walk(startPath)) {
            return stream.filter(Files::isRegularFile)
                    .filter(this::isValidTxtFile)
                    .map(startPath::relativize)
                    .filter(FilePatternUtils::isSprintTask)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidTxtFile(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.endsWith(".txt");
    }
}
