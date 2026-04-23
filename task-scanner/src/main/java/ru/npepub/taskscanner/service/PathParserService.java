package ru.npepub.taskscanner.service;

import lombok.extern.slf4j.Slf4j;
import ru.npepub.taskscanner.dto.SprintTaskInfo;
import ru.npepub.taskscanner.util.FilePatternUtils;
import ru.npepub.taskscanner.util.PathTemplate;

import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;

@Slf4j
public class PathParserService {

    Optional<SprintTaskInfo> parse(Path path, PathTemplate regex) {
        if (path == null || regex == null) {
            return Optional.empty();
        }

        return Optional.of(FilePatternUtils.getMatcher(path, regex))
                .filter(Matcher::matches)
                .map(this::createSprintTaskInfo);
    }

    private SprintTaskInfo createSprintTaskInfo(Matcher matcher) {
        return new SprintTaskInfo(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2))
        );
    }
}
