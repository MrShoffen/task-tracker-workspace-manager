package org.mrshoffen.tasktracker.workspace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.model.dto.create.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkspaceMapper {

    Workspace toEntity(WorkspaceCreateDto workspaceCreateDto, UUID userId);

    WorkspaceResponseDto toDto(Workspace workspace);
}
