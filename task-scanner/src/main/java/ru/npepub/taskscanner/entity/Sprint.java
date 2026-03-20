package ru.npepub.taskscanner.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Sprint {
    private Long id;
    private Long number;
    private LocalDateTime createdAt;
}
