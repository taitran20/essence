package com.tai.essence.helper.mapping;

public interface BaseMapping<E,D> {

    D entityToDto (E entity);
    E dtoToEntity (D dto);

}
