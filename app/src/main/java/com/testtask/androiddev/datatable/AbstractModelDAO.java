package com.testtask.androiddev.datatable;

import java.util.List;
import java.util.logging.Logger;

public interface AbstractModelDAO<T>
{
    List<T> getAll();

    boolean insert(T t);

    boolean update(T t);

    boolean delete(T t);
}