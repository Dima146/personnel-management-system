package com.company.managementsystem.dto.converter;

public interface DtoConverter<E, D> {

    E convertToEntity(D dto);
    D convertToDto(E entity);

}
