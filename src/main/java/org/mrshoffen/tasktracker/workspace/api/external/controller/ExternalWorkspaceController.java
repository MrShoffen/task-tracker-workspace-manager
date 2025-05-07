package org.mrshoffen.tasktracker.workspace.api.external.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.api.external.service.ExternalWorkspaceService;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.dto.links.WorkspaceDtoLinksInjector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;

/**
 * Эндпоинты доступные внешнему клиенту (через gateway)
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces")
public class ExternalWorkspaceController {

    private final WorkspaceDtoLinksInjector linksInjector;

    private final ExternalWorkspaceService taskDeskService;

    @PostMapping
    Mono<ResponseEntity<WorkspaceResponseDto>> createWorkspaceForUser(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                                      @Valid @RequestBody Mono<WorkspaceCreateDto> createDto) {
        return createDto
                .flatMap(dto -> taskDeskService.createWorkspace(dto, userId))
                .map(linksInjector::injectLinks)
                .map(createdSpace ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(createdSpace)
                );
    }

    @GetMapping
    Flux<WorkspaceResponseDto> getAllUserWorkspaces(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId) {
        return taskDeskService
                .getAllUserWorkspaces(userId)
                .map(linksInjector::injectLinks);
    }
}
