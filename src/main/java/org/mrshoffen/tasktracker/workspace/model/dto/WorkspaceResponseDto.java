package org.mrshoffen.tasktracker.workspace.model.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class WorkspaceResponseDto {
    private UUID id;
    private String name;
    private Boolean isPublic;
    private Instant createdAt;
}
