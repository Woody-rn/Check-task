package ru.npepub.taskscanner.repository;

import ru.npepub.taskscanner.entity.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TaskRepository implements BaseRepository<Task, Long> {

    @Override
    public Optional<Task> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<Task> getAll() {
        return List.of();
    }

    @Override
    public Task save(Task entity) {
        entity.setId(1L);
        System.out.println("Task - " + entity.getId());
        return entity;
    }

    @Override
    public Task update(Long aLong, Task entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<Task> findBySprintIdAndTaskNumber(Long sprintId, Long taskNumber) {
        return Optional.empty();
    }
}
