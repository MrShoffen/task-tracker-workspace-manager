package org.mrshoffen.tasktracker.workspace.api.internal.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.exception.AccessDeniedException;
import org.mrshoffen.tasktracker.commons.web.exception.EntityNotFoundException;
import org.mrshoffen.tasktracker.workspace.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalWorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public Mono<Void> ensureUserOwnsWorkspace(UUID userId, UUID workspaceId) {
        return workspaceRepository
                .findById(workspaceId)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Пространство с id '%s' не найдено!"
                                        .formatted(workspaceId.toString())
                        ))
                )
                .flatMap(workspace -> {
                    if (workspace.getUserId().equals(userId)) {
                        return Mono.empty();
                    } else {
                        return Mono.error(new AccessDeniedException(
                                "Данный пользователь не имеет доступ к данному пространству"
                        ));
                    }
                });
    }
}
