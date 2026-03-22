package ru.npepub.taskscanner.util;

import ru.npepub.taskscanner.dto.SprintTaskInfo;

import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilePatternUtils {

    private static final Pattern PATTERN = Pattern.compile(".*?sprint_(\\d+).*?task_(\\d+).*");

    private FilePatternUtils() {
    }

    public static boolean matches(Path path) {
        String string = path.toString();
        return PATTERN.matcher(string).matches();
    }

    public static Optional<SprintTaskInfo> parse(Path path) {
        Matcher matcher = PATTERN.matcher(path.toString());

        if (matcher.matches()) {
            long sprintNum = Long.parseLong(matcher.group(1));
            long taskNum = Long.parseLong(matcher.group(2));
            return Optional.of(
                    new SprintTaskInfo(sprintNum, taskNum));
        }
        return Optional.empty();
    }
}
