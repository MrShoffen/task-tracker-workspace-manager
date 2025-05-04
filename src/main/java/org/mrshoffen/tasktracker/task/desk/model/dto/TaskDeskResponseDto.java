package org.mrshoffen.tasktracker.task.desk.model.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskDeskResponseDto {
    private UUID id;
    private String name;
    private Instant createdAt;
}
