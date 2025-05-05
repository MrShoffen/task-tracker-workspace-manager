package org.mrshoffen.tasktracker.task.desk.service;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.task.desk.exception.BoardAlreadyExistsException;
import org.mrshoffen.tasktracker.task.desk.exception.BoardNotFoundException;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardResponseDto;
import org.mrshoffen.tasktracker.task.desk.model.entity.TaskBoard;
import org.mrshoffen.tasktracker.task.desk.repository.TaskBoardRepository;
import org.mrshoffen.tasktracker.task.desk.util.mapper.TaskBoardMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskBoardService {

    private final TaskBoardMapper taskDeskMapper;

    private final TaskBoardRepository taskBoardRepository;

    public TaskBoardResponseDto createTaskDesk(TaskBoardCreateDto taskBoardCreateDto,
                                               UUID userId) {
        TaskBoard taskBoard = taskDeskMapper.toEntity(taskBoardCreateDto, userId);

        try {
            taskBoardRepository.save(taskBoard);
        } catch (DataIntegrityViolationException e) {
            throw new BoardAlreadyExistsException(
                    "Доска с именем '%s' уже существует у пользователя."
                            .formatted(taskBoardCreateDto.name()));
        }

        return taskDeskMapper.toDto(taskBoard);
    }

    public List<TaskBoardResponseDto> getAllUserDesks(UUID userId) {
        return taskBoardRepository
                .findAllByUserId(userId)
                .stream()
                .map(taskDeskMapper::toDto)
                .toList();
    }

    public TaskBoard getUserTaskDeskWithName(UUID userId, String deskName) {
        return taskBoardRepository
                .findByUserIdAndName(userId, deskName)
                .orElseThrow(() -> new BoardNotFoundException("""
                        Доска задач с id %s не найдена у пользователя
                        """.formatted(userId.toString())
                ));
    }

}
