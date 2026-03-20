package ru.npepub.taskscanner.repository;

import java.util.Collection;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    Optional<T> getById(ID id);

    Collection<T> getAll();

    T save(T entity);

    T update(ID id, T entity);

    boolean delete(ID id);
}