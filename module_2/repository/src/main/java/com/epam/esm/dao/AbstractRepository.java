package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository <T>{

    T create (T model);

    Optional<T> readById (Long id);

    Optional<T> readByName (String name);

    List<T> readAll ();

    Integer delete (Long id);


}
