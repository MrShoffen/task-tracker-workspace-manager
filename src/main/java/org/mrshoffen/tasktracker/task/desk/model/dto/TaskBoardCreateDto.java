package org.mrshoffen.tasktracker.task.desk.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskBoardCreateDto(
        @Size(max = 128, min = 1, message = "Имя доски должно быть между 3 и 128 символами")
        @NotBlank(message = "Имя доски не может быть пустым")
        String name
) {
}
