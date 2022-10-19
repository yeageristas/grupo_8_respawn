package com.respawn.transactional_services;

import com.respawn.entities.GenericModel;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericTransactionalService<E extends GenericModel, ID extends Serializable> {
    List<E> findAll() throws Exception;
    Optional<E> findById(ID id) throws Exception;
    void save(E e) throws Exception;
    void update(ID id, E e) throws Exception;
    void delete(ID id) throws Exception;
}