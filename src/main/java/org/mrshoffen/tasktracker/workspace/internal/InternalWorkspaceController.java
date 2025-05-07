package org.mrshoffen.tasktracker.workspace.internal;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;
import org.mrshoffen.tasktracker.workspace.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/workspaces")
public class InternalWorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/exists")
    Mono<ResponseEntity<Void>> validateStructure(@RequestParam("workspaceId") UUID workspaceId, @RequestParam("userId") UUID userId) {
        return workspaceService
                .getUserWorkspace(userId, workspaceId)
                .thenReturn(ResponseEntity.ok().build());
    }
}
