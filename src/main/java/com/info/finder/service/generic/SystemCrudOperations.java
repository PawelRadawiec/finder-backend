package com.info.finder.service.generic;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SystemCrudOperations<T, ID> {
    T create(T model);

    T update(T model);

    T getById(ID id);

    void delete(ID id);

    Page<T> search(Pageable pageable);

    List<T> getAll();

}
