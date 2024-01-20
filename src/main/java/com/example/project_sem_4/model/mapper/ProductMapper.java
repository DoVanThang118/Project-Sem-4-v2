package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.req.ProductReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper extends MapperEntity<ProductDTO, Product>{

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product mapReqToEntity(ProductReq req);
}
