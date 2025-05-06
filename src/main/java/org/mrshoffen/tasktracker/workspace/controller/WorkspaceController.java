package org.mrshoffen.tasktracker.workspace.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceCreateDto;
import org.mrshoffen.tasktracker.workspace.model.dto.WorkspaceResponseDto;
import org.mrshoffen.tasktracker.workspace.service.WorkspaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService taskDeskService;

    @PostMapping
    Mono<ResponseEntity<WorkspaceResponseDto>> createWorkspace(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                               @Valid @RequestBody Mono<WorkspaceCreateDto> createDto) {
        return createDto
                .flatMap(dto -> taskDeskService.createWorkspace(dto, userId))
                .map(createdSpace ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(createdSpace)
                );
    }

    @GetMapping
    Flux<WorkspaceResponseDto> getAllWorkspaces(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId) {
        return taskDeskService.getAllUserDesks(userId);
    }
}
