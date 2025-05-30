package org.mrshoffen.tasktracker.workspace.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceCreatedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceDeletedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceUpdatedEvent;
import org.mrshoffen.tasktracker.commons.web.dto.WorkspaceResponseDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkspaceEventPublisher {

    private final KafkaTemplate<UUID, Object> kafkaTemplate;


    public void publishWorkspaceDeletedEvent(UUID userId, UUID workspaceId) {
        WorkspaceDeletedEvent event = new WorkspaceDeletedEvent(userId, workspaceId);
        log.info("Event published to kafka topic '{}' - {}", WorkspaceDeletedEvent.TOPIC, event);
        kafkaTemplate.send(WorkspaceDeletedEvent.TOPIC, event.getWorkspaceId(), event);
    }

    public void publishWorkspaceCreatedEvent(WorkspaceResponseDto workspace) {
        WorkspaceCreatedEvent event = new WorkspaceCreatedEvent(workspace);
        log.info("Event published to kafka topic '{}' - {}", WorkspaceCreatedEvent.TOPIC, event);
        kafkaTemplate.send(WorkspaceCreatedEvent.TOPIC, workspace.getId(), event);
    }

    public void publishWorkspaceUpdatedEvent(UUID workspaceId, String fieldName, Object newValue, UUID updatedBy) {
        WorkspaceUpdatedEvent event = WorkspaceUpdatedEvent.builder()
                .updatedBy(updatedBy)
                .workspaceId(workspaceId)
                .updatedAt(Instant.now())
                .updatedField(Map.of(fieldName, newValue))
                .build();
        log.info("Event published to kafka topic '{}' - {}", WorkspaceUpdatedEvent.TOPIC, event);
        kafkaTemplate.send(WorkspaceUpdatedEvent.TOPIC, workspaceId, event);
    }

}
