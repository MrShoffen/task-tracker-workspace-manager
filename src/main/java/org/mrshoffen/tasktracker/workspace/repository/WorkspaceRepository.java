package org.mrshoffen.tasktracker.workspace.repository;

import org.mrshoffen.tasktracker.workspace.model.entity.Workspace;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends ReactiveCrudRepository<Workspace, UUID> {

    Mono<Workspace> findByUserIdAndName(UUID userId, String workspaceName);

    Flux<Workspace> findAllByUserId(UUID userId);
}
