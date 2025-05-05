package org.mrshoffen.tasktracker.task.desk.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boards",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "name"}
                )
        }
)
public class TaskBoard {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
