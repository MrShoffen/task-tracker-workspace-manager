package org.mrshoffen.tasktracker.workspace.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("workspaces")
public class Workspace {

    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("is_public")
    private Boolean isPublic = false;

    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Column("user_id")
    private UUID userId;
}
