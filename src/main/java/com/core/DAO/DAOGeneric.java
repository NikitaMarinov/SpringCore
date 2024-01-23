package com.core.DAO;

import java.util.List;
import java.util.Optional;

public interface DAOGeneric<T, ID> {
    Optional<T> findById(ID id);
    Optional<List<T>> findAll();
    Optional<T> save(T entity);
    Optional<T> update(T entity);
    void delete(ID id);
}