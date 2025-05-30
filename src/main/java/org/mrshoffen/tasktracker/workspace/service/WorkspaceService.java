package org.mrshoffen.tasktracker.workspace.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.commons.web.exception.EntityAlreadyExistsException;
import org.mrshoffen.tasktracker.commons.web.exception.EntityNotFoundException;
import org.mrshoffen.tasktracker.workspace.event.WorkspaceEventPublisher;
import org.mrshoffen.tasktracker.workspace.mapper.WorkspaceMapper;
import org.mrshoffen.tasktracker.workspace.model.dto.create.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.AccessEditDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.CoverEditDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.NameEditDto;
import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;
import org.mrshoffen.tasktracker.workspace.repository.WorkspaceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceMapper taskDeskMapper;

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceEventPublisher workspaceEventPublisher;

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
                .map(taskDeskMapper::toDto)
                .doOnSuccess(workspaceEventPublisher::publishWorkspaceCreatedEvent);
    }

    public Flux<WorkspaceResponseDto> getAllUserWorkspaces(UUID userId) {
        return workspaceRepository
                .findAllByUserId(userId)
                .map(taskDeskMapper::toDto);
    }

    public Mono<Void> deleteUserWorkspace(UUID userId, UUID workspaceId) {
        return workspaceRepository
                .findById(workspaceId)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Пространство с id '%s' не найдено!"
                                        .formatted(workspaceId.toString())
                        ))
                )
                .flatMap(workspaceRepository::delete)
                .doOnSuccess(ws ->
                        workspaceEventPublisher.publishWorkspaceDeletedEvent(userId, workspaceId)
                );
    }

    public Mono<WorkspaceResponseDto> getWorkspace(UUID workspaceId) {
        return workspaceRepository
                .findById(workspaceId)
                .map(taskDeskMapper::toDto)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Пространство с именем %s не найдено"
                                        .formatted(workspaceId.toString())
                        ))
                );
    }

    public Mono<WorkspaceResponseDto> updateName(UUID workspaceId, NameEditDto dto, UUID userId) {
        return getWorkspaceOrThrow(workspaceId)
                .flatMap(ws -> {
                    ws.setName(dto.newName());
                    return workspaceRepository.save(ws);
                })
                .onErrorMap(DuplicateKeyException.class, e ->
                        new EntityAlreadyExistsException(
                                "Пространство с именем '%s' уже существует"
                                        .formatted(dto.newName())
                        )
                )
                .map(taskDeskMapper::toDto)
                .doOnSuccess(ws -> workspaceEventPublisher
                        .publishWorkspaceUpdatedEvent(workspaceId, "name", dto.newName(), userId));

    }

    public Mono<WorkspaceResponseDto> updateAccess(UUID workspaceId, AccessEditDto dto, UUID userId) {
        return getWorkspaceOrThrow(workspaceId)
                .flatMap(ws -> {
                    ws.setIsPublic(dto.isPublic());
                    return workspaceRepository.save(ws);
                })
                .map(taskDeskMapper::toDto)
                .doOnSuccess(ws -> workspaceEventPublisher
                        .publishWorkspaceUpdatedEvent(workspaceId, "isPublic", dto.isPublic(), userId));

    }

    public Mono<WorkspaceResponseDto> updateCover(UUID workspaceId, CoverEditDto dto, UUID userId) {
        return getWorkspaceOrThrow(workspaceId)
                .flatMap(ws -> {
                    ws.setCoverUrl(dto.newCoverUrl());
                    return workspaceRepository.save(ws);
                })
                .map(taskDeskMapper::toDto)
                .doOnSuccess(ws -> workspaceEventPublisher
                        .publishWorkspaceUpdatedEvent(workspaceId, "coverUrl", dto.newCoverUrl(), userId));

    }

    private Mono<Workspace> getWorkspaceOrThrow(UUID workspaceId) {
        return workspaceRepository
                .findById(workspaceId)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Пространство с id %s не найдена в данном пространстве"
                                        .formatted(workspaceId.toString())
                        ))
                );
    }
}
