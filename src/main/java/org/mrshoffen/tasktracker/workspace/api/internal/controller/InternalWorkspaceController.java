package org.mrshoffen.tasktracker.workspace.api.internal.controller;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.workspace.api.internal.service.InternalWorkspaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;


/**
 * Эндпоинты для взаимодействия между микросервисами по бизнес логике
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/workspaces")
public class InternalWorkspaceController {

    private final InternalWorkspaceService workspaceService;

    /**
     * Необходим для проверки наличия workspace у заданного юзера
     */
    @GetMapping("/{userId}/{workspaceId}")
    public Mono<Void> ensureUserOwnsWorkspace(@PathVariable("userId") UUID userId,
                                              @PathVariable("workspaceId") UUID workspaceId) {
        return workspaceService
                .ensureUserOwnsWorkspace(userId, workspaceId);
    }


}
