package org.mrshoffen.tasktracker.workspace.api.bff.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.commons.web.exception.EntityNotFoundException;
import org.mrshoffen.tasktracker.workspace.mapper.WorkspaceMapper;
import org.mrshoffen.tasktracker.workspace.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceAggregationService {

    private final WorkspaceMapper taskDeskMapper;

    private final WorkspaceRepository workspaceRepository;

    public Mono<WorkspaceResponseDto> getWorkspace(UUID workspaceId) {
        return workspaceRepository
                .findById(workspaceId)
                .map(taskDeskMapper::toDto)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Пространство с именем %s не найдено у пользователя"
                                        .formatted(workspaceId.toString())
                        ))
                );
    }
}
