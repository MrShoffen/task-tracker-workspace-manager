package org.mrshoffen.tasktracker.workspace.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.model.dto.create.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.AccessEditDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.CoverEditDto;
import org.mrshoffen.tasktracker.workspace.model.dto.edit.NameEditDto;
import org.mrshoffen.tasktracker.workspace.service.PermissionsService;
import org.mrshoffen.tasktracker.workspace.service.WorkspaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;
import static org.mrshoffen.tasktracker.commons.web.permissions.Permission.*;

/**
 * Эндпоинты доступные внешнему клиенту (через gateway)
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces")
public class ExternalWorkspaceController {

    private final WorkspaceService workspaceService;

    private final PermissionsService permissionsService;

    @PostMapping
    Mono<ResponseEntity<WorkspaceResponseDto>> createWorkspaceForUser(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                                      @Valid @RequestBody Mono<WorkspaceCreateDto> createDto) {
        return createDto
                .flatMap(dto -> workspaceService.createWorkspace(dto, userId))
                .map(createdSpace ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(createdSpace)
                );
    }

    @PatchMapping("/{workspaceId}/name")
    Mono<WorkspaceResponseDto> updateWorkspaceName(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                   @PathVariable UUID workspaceId,
                                                   @Valid @RequestBody Mono<NameEditDto> nameEditDto) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, UPDATE_WORKSPACE_NAME)
                .then(nameEditDto
                        .flatMap(dto -> workspaceService.updateName(workspaceId, dto, userId)));
    }

    @PatchMapping("/{workspaceId}/access")
    Mono<WorkspaceResponseDto> updateWorkspaceAccess(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                     @PathVariable UUID workspaceId,
                                                     @Valid @RequestBody Mono<AccessEditDto> accessEditDto) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, UPDATE_WORKSPACE_ACCESS)
                .then(accessEditDto
                        .flatMap(dto -> workspaceService.updateAccess(workspaceId, dto, userId)));
    }

    @PatchMapping("/{workspaceId}/cover")
    Mono<WorkspaceResponseDto> updateWorkspaceCover(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                    @PathVariable UUID workspaceId,
                                                    @Valid @RequestBody Mono<CoverEditDto> accessEditDto) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, UPDATE_WORKSPACE_COVER)
                .then(accessEditDto
                        .flatMap(dto -> workspaceService.updateCover(workspaceId, dto, userId)));
    }

    @GetMapping
    Flux<WorkspaceResponseDto> getAllUserWorkspaces(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId) {
        return workspaceService
                .getAllUserWorkspaces(userId);
    }

    @DeleteMapping("/{workspaceId}")
    Mono<ResponseEntity<Void>> deleteWorkspace(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                               @PathVariable UUID workspaceId) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, DELETE_WORKSPACE)
                .then(workspaceService.deleteUserWorkspace(userId, workspaceId))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
