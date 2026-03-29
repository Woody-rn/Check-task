package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.Sprint;
import ru.npepub.taskscanner.entity.Task;
import ru.npepub.taskscanner.repository.TaskRepository;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getOrCreate(Sprint sprint, Long taskNum) {
        return taskRepository
                .findBySprintIdAndTaskNumber(sprint.getId(), taskNum)
                .orElseGet(() -> {
                    System.out.println("work save TaskService");
                    Task newTask = Task.builder()
                            .sprintId(sprint.getId())
                            .number(taskNum)
                            .build();
                    return taskRepository.save(newTask);
                });
    }
}
