package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.model.dto.ImageDTO;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.req.ImageReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper extends MapperEntity<ImageDTO, Image>{

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    Image mapReqToEntity(ImageReq req);
}
