package org.mrshoffen.tasktracker.workspace.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.registration.RegistrationAttemptEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceCreatedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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

    public void publishWorkspaceCreatedEvent(UUID userId, UUID workspaceId) {
        WorkspaceCreatedEvent event = new WorkspaceCreatedEvent(userId, workspaceId);
        log.info("Event published to kafka topic '{}' - {}", WorkspaceCreatedEvent.TOPIC, event);
        kafkaTemplate.send(WorkspaceCreatedEvent.TOPIC, event.getWorkspaceId(), event);
    }

}
