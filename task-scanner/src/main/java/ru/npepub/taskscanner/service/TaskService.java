package ru.npepub.taskscanner.service;

import lombok.extern.slf4j.Slf4j;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.repository.TaskRepository;

@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    TaskEntity findOrSave(SprintEntity sprint, Integer taskNum) {
        log.debug("Requesting task: sprintId={}, taskNumber={}", sprint.getId(), taskNum);

        return taskRepository
                .findBySprintIdAndTaskNumber(sprint.getId(), taskNum)
                .map(taskEntity -> {
                    log.debug("Task found: id={}, sprintId={}, number={}",
                            taskEntity.getId(), taskEntity.getSprintId(), taskEntity.getNumber());
                    return taskEntity;
                })
                .orElseGet(() -> {
                    log.debug("Task not found, creating new: sprintId={}, number={}",
                            sprint.getId(), taskNum);
                    TaskEntity newTask = TaskEntity.builder()
                            .sprintId(sprint.getId())
                            .number(taskNum)
                            .build();

                    TaskEntity saved = taskRepository.save(newTask);
                    log.debug("Task created: id={}, sprintId={}, number={}",
                            saved.getId(), saved.getSprintId(), saved.getNumber());
                    return saved;
                });
    }
}
