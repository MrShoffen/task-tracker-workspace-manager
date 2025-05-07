package org.mrshoffen.tasktracker.workspace.api.bff.controller;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.api.bff.service.WorkspaceAggregationService;
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

    private final WorkspaceAggregationService taskDeskService;

    private final WorkspaceDtoLinksInjector linksInjector;

    /**
     * Необходим для агрегации данных workspace
     */
    @GetMapping("/{workspaceId}/full")
    public Mono<WorkspaceResponseDto> getWorkspaceById(@PathVariable("workspaceId") UUID workspaceId) {
        return taskDeskService
                .getWorkspace(workspaceId)
                .map(linksInjector::injectLinks);
    }


}
