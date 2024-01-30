package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.req.ProductReq;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

public interface BrandMapper extends MapperEntity<BrandDTO, Brand>{

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    Brand mapReqToEntity(BrandReq req);
}
