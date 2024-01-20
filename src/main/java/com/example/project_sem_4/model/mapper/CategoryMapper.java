package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.req.CategoryReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper extends MapperEntity<CategoryDTO, Category> {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapReqToEntity(CategoryReq req);

}
