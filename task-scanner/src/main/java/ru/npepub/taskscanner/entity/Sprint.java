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
public class Sprint {
    private Long id;
    private Long number;
    private LocalDateTime createdAt;
}
