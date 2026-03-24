package ru.npepub.taskscanner.repository;

import ru.npepub.taskscanner.config.db.DatabaseConfig;
import ru.npepub.taskscanner.entity.Sprint;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SprintRepository implements BaseRepository<Sprint, Long> {


    public SprintRepository(DatabaseConfig databaseConfig) {
    }

    @Override
    public Optional<Sprint> getById(Long id) {
        return Optional.empty();
    }

    public Optional<Sprint> findByNumber(Long number) {
        return Optional.empty();
    }


    @Override
    public Collection<Sprint> getAll() {
        return List.of();
    }

    @Override
    public Sprint save(Sprint entity) {
        entity.setId(1L);
        System.out.println("Sprint - " + entity.getId());
        return entity;
    }

    @Override
    public Sprint update(Long id, Sprint entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

}
