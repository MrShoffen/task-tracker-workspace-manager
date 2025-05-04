package org.mrshoffen.tasktracker.task.desk.repository;

import org.mrshoffen.tasktracker.task.desk.model.entity.TaskDesk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDeskRepository extends JpaRepository<TaskDesk, UUID> {

    Optional<TaskDesk> findByUserIdAndName(UUID userId, String name);

    List<TaskDesk> findAllByUserId(UUID userId);
}
