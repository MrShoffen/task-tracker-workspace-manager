package org.mrshoffen.tasktracker.task.desk.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardResponseDto;
import org.mrshoffen.tasktracker.task.desk.model.entity.TaskBoard;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskBoardMapper {

    TaskBoard toEntity(TaskBoardCreateDto taskBoardCreateDto, UUID userId);

    TaskBoardResponseDto toDto(TaskBoard taskBoard);
}
