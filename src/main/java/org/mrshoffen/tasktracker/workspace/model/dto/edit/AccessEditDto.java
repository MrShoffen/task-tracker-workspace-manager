package org.mrshoffen.tasktracker.workspace.model.dto.edit;

import jakarta.validation.constraints.NotNull;

public record AccessEditDto(
        @NotNull(message = "Необходимо указать уровень приватности пространства")
        Boolean isPublic
) {
}
