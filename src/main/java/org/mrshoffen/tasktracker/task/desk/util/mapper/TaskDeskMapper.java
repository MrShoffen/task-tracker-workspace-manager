package org.mrshoffen.tasktracker.task.desk.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskResponseDto;
import org.mrshoffen.tasktracker.task.desk.model.entity.TaskDesk;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskDeskMapper {

    TaskDesk toEntity(TaskDeskCreateDto taskDeskCreateDto, UUID userId);

    TaskDeskResponseDto toDto(TaskDesk taskDesk);
}
