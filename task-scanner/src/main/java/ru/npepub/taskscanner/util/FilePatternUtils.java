package ru.npepub.taskscanner.util;

import ru.npepub.taskscanner.dto.SprintTaskInfo;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilePatternUtils {

    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    private FilePatternUtils() {
    }

    private static Pattern getPattern(String regex) {
        return PATTERN_CACHE.computeIfAbsent(regex, Pattern::compile);
    }

    private static Pattern getPattern(RegexPattern regexPattern) {
        return getPattern(regexPattern.getRegex());
    }

    public static boolean matches(Path path, String regex) {
        if (regex == null || path == null) {
            return false;
        }

        return getPattern(regex)
                .matcher(path.toString())
                .matches();
    }

    public static boolean matches(Path path, RegexPattern regex) {
        return matches(path, regex.getRegex());
    }

    public static boolean matchesSprintTask(Path path) {
        return matches(path, RegexPattern.SPRINT_TASK);
    }

    public static Optional<SprintTaskInfo> parse(Path path) {
        return Optional.of(getPattern(RegexPattern.SPRINT_TASK)
                        .matcher(path.toString()))
                .filter(Matcher::matches)
                .map(matcher -> new SprintTaskInfo(
                        Long.parseLong(matcher.group(1)),
                        Long.parseLong(matcher.group(2))
                ));
    }
}
