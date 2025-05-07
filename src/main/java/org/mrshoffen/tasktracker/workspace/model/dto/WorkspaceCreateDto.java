package org.mrshoffen.tasktracker.workspace.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WorkspaceCreateDto(
        @Size(max = 128, min = 1, message = "Имя доски должно быть между 1 и 128 символами")
        @NotBlank(message = "Имя доски не может быть пустым")
        String name,

        @NotNull(message = "Необходимо указать уровень приватности пространства")
        Boolean isPublic
) {
}
