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
public class FileMetaData {
    private Long id;
    private Long taskId;
    private Long sprintId;
    private String fileName;
    private String s3Key;
    private String s3Url;

    private LocalDateTime createdAt;
}
