package ru.npepub.taskscanner.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Task {
    private Long id;
    private Long number;
    private Long sprintId;
    private LocalDateTime createdAt;
}
