package com.example.project_sem_4.model.mapper;

public interface MapperEntity<D,E> {
    D mapEntityToDTO(E entity);
    E mapDTOToEntity(D dto);
}
