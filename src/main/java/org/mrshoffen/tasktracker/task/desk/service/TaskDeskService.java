package org.mrshoffen.tasktracker.task.desk.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.task.desk.exception.DeskAlreadyExistsException;
import org.mrshoffen.tasktracker.task.desk.exception.DeskNotFoundException;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskResponseDto;
import org.mrshoffen.tasktracker.task.desk.model.entity.TaskDesk;
import org.mrshoffen.tasktracker.task.desk.repository.TaskDeskRepository;
import org.mrshoffen.tasktracker.task.desk.util.mapper.TaskDeskMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskDeskService {

    private final TaskDeskMapper taskDeskMapper;

    private final TaskDeskRepository taskDeskRepository;

    public TaskDeskResponseDto createTaskDesk(TaskDeskCreateDto taskDeskCreateDto,
                                              UUID userId) {
        TaskDesk taskDesk = taskDeskMapper.toEntity(taskDeskCreateDto, userId);

        try {
            taskDeskRepository.save(taskDesk);
        } catch (DataIntegrityViolationException e) {
            throw new DeskAlreadyExistsException(
                    "Доска с именем '%s' уже существует у пользователя."
                            .formatted(taskDeskCreateDto.name()));
        }

        return taskDeskMapper.toDto(taskDesk);
    }

    public List<TaskDeskResponseDto> getAllUserDesks(UUID userId) {
        return taskDeskRepository
                .findAllByUserId(userId)
                .stream()
                .map(taskDeskMapper::toDto)
                .toList();
    }

    private TaskDesk getUserTaskDeskWithName(UUID userId, String deskName) {
        return taskDeskRepository
                .findByUserIdAndName(userId, deskName)
                .orElseThrow(() -> new DeskNotFoundException("""
                        Доска задач с id %s не найдена у пользователя
                        """.formatted(userId.toString())
                ));
    }
}
