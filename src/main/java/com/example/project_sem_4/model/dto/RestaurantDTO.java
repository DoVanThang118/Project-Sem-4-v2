package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Image;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Long id;

    private String name;

    private String description;

    private String tel;

    private String address;

    private Integer status;

    private BrandDTO brand;

    private Set<ImageDTO> images;
}
