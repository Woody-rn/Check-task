package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.entity.TaskEntity;
import ru.npepub.taskscanner.repository.TaskRepository;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskEntity getOrCreate(SprintEntity sprint, Long taskNum) {
        return taskRepository
                .findBySprintIdAndTaskNumber(sprint.getId(), taskNum)
                .orElseGet(() -> {
                    System.out.println("work save TaskService");
                    TaskEntity newTask = TaskEntity.builder()
                            .sprintId(sprint.getId())
                            .number(taskNum)
                            .build();
                    return taskRepository.save(newTask);
                });
    }
}
