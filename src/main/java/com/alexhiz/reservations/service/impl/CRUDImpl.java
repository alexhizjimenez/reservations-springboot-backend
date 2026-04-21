package com.alexhiz.reservations.service.impl;

import java.util.List;

import com.alexhiz.reservations.exception.ModelNotFoundException;
import com.alexhiz.reservations.repo.IGenericRepo;
import com.alexhiz.reservations.service.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public T update(ID id, T entity) throws Exception {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Entity not found " + id));
        return getRepo().save(entity);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Entity not found " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Entity not found " + id));
        getRepo().deleteById(id);
    }

}
