package ru.npepub.taskscanner.util;

import lombok.Getter;

@Getter
public enum PathTemplate {

    SPRINT_TASK(".*?sprint_(\\d+)[\\\\/]task_(\\d+).*"),
    TASK_FILE_NO_SUBFOLDERS(".*?sprint_(\\d+)[\\\\/]task_(\\d+)[\\\\/][^\\\\/]+$");

    private final String regex;

    PathTemplate(String regex) {
        this.regex = regex;
    }
}
