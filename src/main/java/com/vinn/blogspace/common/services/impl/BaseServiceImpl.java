package com.vinn.blogspace.common.services.impl;

import com.vinn.blogspace.common.exceptions.ResourceNotFoundException;
import com.vinn.blogspace.common.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    protected abstract String getEntityName();

    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), "id", id));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T update(ID id, T entity) {
        ensureResourceExists(id);
        return getRepository().save(entity);
    }

    @Override
    public void delete(ID id) {
        ensureResourceExists(id);
        getRepository().deleteById(id);
    }

    private void ensureResourceExists(ID id) {
        if (!getRepository().existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), "id", id);
        }
    }
}