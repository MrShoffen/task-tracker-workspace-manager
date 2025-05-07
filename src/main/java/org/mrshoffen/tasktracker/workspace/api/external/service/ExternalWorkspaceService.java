package org.mrshoffen.tasktracker.workspace.api.external.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.commons.web.exception.EntityAlreadyExistsException;
import org.mrshoffen.tasktracker.workspace.mapper.WorkspaceMapper;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;
import org.mrshoffen.tasktracker.workspace.repository.WorkspaceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalWorkspaceService {

    private final WorkspaceMapper taskDeskMapper;

    private final WorkspaceRepository workspaceRepository;

    public Mono<WorkspaceResponseDto> createWorkspace(WorkspaceCreateDto workspaceCreateDto,
                                                      UUID userId) {
        Workspace workspace = taskDeskMapper.toEntity(workspaceCreateDto, userId);

        return workspaceRepository
                .save(workspace)
                .onErrorMap(DataIntegrityViolationException.class, e ->
                        new EntityAlreadyExistsException(
                                "Пространство с именем '%s' уже существует у пользователя."
                                        .formatted(workspaceCreateDto.name()))
                )
                .map(taskDeskMapper::toDto);
    }

    public Flux<WorkspaceResponseDto> getAllUserWorkspaces(UUID userId) {
        return workspaceRepository
                .findAllByUserId(userId)
                .map(taskDeskMapper::toDto);
    }

}
