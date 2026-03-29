package ru.npepub.taskscanner.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Task {
    private Long id;
    private Long number;
    private Long sprintId;
    private LocalDateTime createdAt;
}
