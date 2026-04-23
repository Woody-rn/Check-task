package ru.npepub.taskscanner.util;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilePatternUtils {

    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    private FilePatternUtils() {
    }

    public static Matcher getMatcher(Path path, PathTemplate regex) {
        return getPattern(regex)
                .matcher(path.toString());
    }

    public static boolean matches(Path path, PathTemplate regex) {
        return matches(path, regex.getRegex());
    }

    public static boolean matches(Path path, String regex) {
        if (regex == null || path == null) {
            return false;
        }
        return getPattern(regex)
                .matcher(path.toString())
                .matches();
    }

    private static Pattern getPattern(String regex) {
        return PATTERN_CACHE.computeIfAbsent(regex, Pattern::compile);
    }

    private static Pattern getPattern(PathTemplate regexPattern) {
        return getPattern(regexPattern.getRegex());
    }
}
