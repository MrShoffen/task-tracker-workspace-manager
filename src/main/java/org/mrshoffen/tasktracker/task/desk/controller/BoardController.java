package org.mrshoffen.tasktracker.task.desk.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardCreateDto;
import org.mrshoffen.tasktracker.task.desk.model.dto.TaskBoardResponseDto;
import org.mrshoffen.tasktracker.task.desk.service.TaskBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final TaskBoardService taskDeskService;

    @PostMapping
    ResponseEntity<TaskBoardResponseDto> createTaskDesk(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                        @Valid @RequestBody TaskBoardCreateDto createDto) {
        TaskBoardResponseDto taskDesk = taskDeskService.createTaskDesk(createDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskDesk);
    }

    @GetMapping
    ResponseEntity<List<TaskBoardResponseDto>> getAllTaskDesks(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId){
        List<TaskBoardResponseDto> allUserDesks = taskDeskService.getAllUserDesks(userId);
        return ResponseEntity.ok(allUserDesks);
    }
}
