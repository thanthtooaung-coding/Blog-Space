package com.vinn.blogspace.common.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface BaseService<T, ID extends Serializable> {
    T findById(ID id);
    Page<T> findAll(Pageable pageable);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}