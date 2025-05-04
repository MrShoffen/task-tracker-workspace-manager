package org.mrshoffen.tasktracker.task.desk.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskDeskResponseDto;
import org.mrshoffen.tasktracker.task.desk.service.TaskDeskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class DeskController {

    private final TaskDeskService taskDeskService;

    @PostMapping
    ResponseEntity<TaskDeskResponseDto> createTaskDesk(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                       @Valid @RequestBody TaskDeskCreateDto createDto) {
        TaskDeskResponseDto taskDesk = taskDeskService.createTaskDesk(createDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskDesk);
    }

    @GetMapping
    ResponseEntity<List<TaskDeskResponseDto>> getAllTaskDesks(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId){
        List<TaskDeskResponseDto> allUserDesks = taskDeskService.getAllUserDesks(userId);
        return ResponseEntity.ok(allUserDesks);
    }
}
