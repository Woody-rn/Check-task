package ru.npepub.taskscanner.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TaskEntity {
    private Long id;
    private Long sprintId;
    private Long number;
    private LocalDateTime createdAt;
}
