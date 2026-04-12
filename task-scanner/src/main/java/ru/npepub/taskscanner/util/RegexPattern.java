package ru.npepub.taskscanner.util;


import lombok.Getter;

@Getter
public enum RegexPattern {

    SPRINT_TASK(".*?sprint_(\\d+).*?task_(\\d+).*"),
    EXACT_TASK_FILE("sprint_(\\d+)[\\\\/]task_(\\d+)[\\\\/][^\\\\/]+\\.txt$");

    private final String regex;

    RegexPattern(String regex) {
        this.regex = regex;
    }
}
