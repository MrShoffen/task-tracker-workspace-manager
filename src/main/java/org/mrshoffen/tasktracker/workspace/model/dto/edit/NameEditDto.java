package org.mrshoffen.tasktracker.workspace.model.dto.edit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NameEditDto (
        @Size(max = 128, min = 1, message = "Имя доски должно быть между 1 и 128 символами")
        @NotBlank(message = "Имя доски не может быть пустым")
        String newName
){

}
