package org.mrshoffen.tasktracker.workspace.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.workspace.exception.WorkspaceAlreadyExistsException;
import org.mrshoffen.tasktracker.workspace.exception.WorkspaceNotFoundException;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;
import org.mrshoffen.tasktracker.workspace.repository.WorkspaceRepository;
import org.mrshoffen.tasktracker.workspace.util.mapper.WorkspaceMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceMapper taskDeskMapper;

    private final WorkspaceRepository workspaceRepository;

    public Mono<WorkspaceResponseDto> createWorkspace(WorkspaceCreateDto workspaceCreateDto,
                                                      UUID userId) {
        Workspace workspace = taskDeskMapper.toEntity(workspaceCreateDto, userId);

        return workspaceRepository
                .save(workspace)
                .onErrorMap(DataIntegrityViolationException.class, e ->
                        new WorkspaceAlreadyExistsException(
                                "Пространство с именем '%s' уже существует у пользователя."
                                        .formatted(workspaceCreateDto.name()))
                )
                .map(taskDeskMapper::toDto);
    }

    public Flux<WorkspaceResponseDto> getAllUserDesks(UUID userId) {
        return workspaceRepository
                .findAllByUserId(userId)
                .map(taskDeskMapper::toDto);
    }


    public Mono<Workspace> getUserWorkspaceWithName(UUID userId, String workspaceName) {
        return workspaceRepository
                .findByUserIdAndName(userId, workspaceName)
                .switchIfEmpty(Mono.error(new WorkspaceNotFoundException("Пространство с именем %s не найдено у пользователя")));
    }

}
