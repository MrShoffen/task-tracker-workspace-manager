package org.mrshoffen.tasktracker.workspace.controller;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.service.WorkspaceService;
import org.mrshoffen.tasktracker.workspace.model.dto.links.WorkspaceDtoLinksInjector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;


/**
 * Эндпоинты для агрегирующих сервисов.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/aggregate-api/workspaces")
public class WorkspaceAggregationController {

    private final WorkspaceService workspaceService;

    private final WorkspaceDtoLinksInjector linksInjector;

    /**
     * Необходим для агрегации данных workspace
     */
    @GetMapping("/{workspaceId}")
    public Mono<WorkspaceResponseDto> getWorkspaceById(@PathVariable("workspaceId") UUID workspaceId) {
        return workspaceService
                .getWorkspace(workspaceId)
                .map(linksInjector::injectLinks);
    }


}
