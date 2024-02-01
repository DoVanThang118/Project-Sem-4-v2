package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.req.BrandReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper extends MapperEntity<BrandDTO, Brand>{

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    Brand mapReqToEntity(BrandReq req);
}
