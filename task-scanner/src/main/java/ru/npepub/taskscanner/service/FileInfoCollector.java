package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.dto.FileInfo;
import ru.npepub.taskscanner.util.PathTemplate;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileInfoCollector {

    private final FileSearchService fileSearchService;
    private final PathParserService pathParserService;

    public FileInfoCollector(FileSearchService fileSearchService,
                             PathParserService pathParserService) {
        this.fileSearchService = fileSearchService;
        this.pathParserService = pathParserService;
    }

    public List<FileInfo> collect(String pathToDirectory) {
        Path basePath = Path.of(pathToDirectory);
        List<Path> foundFiles = fileSearchService.findAbsolutePaths(
                basePath);

        return foundFiles.stream()
                .map(absolutePath -> toFileInfo(absolutePath, basePath))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<FileInfo> toFileInfo(Path absolutePath, Path basePath) {
        return pathParserService.parse(absolutePath, PathTemplate.TASK_FILE_NO_SUBFOLDERS)
                .map(sprintTaskInfo -> new FileInfo(
                        sprintTaskInfo.sprintNum(),
                        sprintTaskInfo.taskNum(),
                        absolutePath.getFileName().toString(),
                        absolutePath,
                        basePath.relativize(absolutePath)
                ));
    }
}
