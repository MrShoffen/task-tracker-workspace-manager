package org.mrshoffen.tasktracker.task.desk.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskDeskCreateDto(
        @Size(max = 128, min = 1, message = "Имя доски должно быть между 3 и 128 символами")
        @NotNull(message = "Имя доски не может быть пустым")
        String name
) {
}
