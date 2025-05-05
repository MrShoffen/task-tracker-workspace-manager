package org.mrshoffen.tasktracker.task.desk.repository;

import org.mrshoffen.tasktracker.task.desk.model.entity.TaskBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskBoardRepository extends JpaRepository<TaskBoard, UUID> {

    Optional<TaskBoard> findByUserIdAndName(UUID userId, String name);

    List<TaskBoard> findAllByUserId(UUID userId);
}
