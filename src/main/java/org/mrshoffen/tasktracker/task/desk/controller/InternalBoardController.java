package org.mrshoffen.tasktracker.task.desk.controller;


import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.task.desk.service.TaskBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/boards")
public class InternalBoardController {

    private final TaskBoardService taskBoardService;

    @GetMapping("/id")
    String boardId(@RequestParam("boardName") String boardName, @RequestParam("userId") UUID userId) {
        return taskBoardService
                .getUserTaskDeskWithName(userId, boardName)
                .getId().toString();
    }
}
