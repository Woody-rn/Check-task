package ru.npepub.taskscanner.dto;

import lombok.Getter;

@Getter
public class ScanRequest {
    private String path;
    private boolean recursive;
}
