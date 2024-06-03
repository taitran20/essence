package com.tai.essence.service;

import com.tai.essence.exception.DuplicateNameException;

import java.util.List;

public interface BaseService<T,K> {

    List<T> findAll();
    T findById(K id);
    boolean insert(T dto) throws DuplicateNameException;
    boolean update(K id, T dto);
    boolean delete(K id);
}