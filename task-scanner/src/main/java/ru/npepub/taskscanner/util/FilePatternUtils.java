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

    public static Optional<SprintTaskInfo> parse(Path path, RegexPattern regex) {
        if (path == null || regex == null) {
            return Optional.empty();
        }
        return Optional.of(getMatcher(path, regex))
                .filter(Matcher::matches)
                .map(FilePatternUtils::createSprintTaskInfo);
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

    public static boolean isSprintTask(Path path) {
        return matches(path, RegexPattern.SPRINT_TASK);
    }

    private static SprintTaskInfo createSprintTaskInfo(Matcher matcher) {
        return new SprintTaskInfo(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2))
        );
    }

    private static Matcher getMatcher(Path path, RegexPattern regex) {
        return getPattern(regex)
                .matcher(path.toString());
    }

    private static Pattern getPattern(String regex) {
        return PATTERN_CACHE.computeIfAbsent(regex, Pattern::compile);
    }

    private static Pattern getPattern(RegexPattern regexPattern) {
        return getPattern(regexPattern.getRegex());
    }
}
