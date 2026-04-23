package ru.npepub.taskscanner.util;

import lombok.Getter;

@Getter
public enum PathTemplate {

    SPRINT_TASK(".*?sprint_(\\d+).*?task_(\\d+).*"),
    SPRINT_TASK_FILE(".*?sprint_(\\d+)[\\\\/]task_(\\d+)[\\\\/][^\\\\/]+\\.txt$");

    private final String regex;

    PathTemplate(String regex) {
        this.regex = regex;
    }
}
