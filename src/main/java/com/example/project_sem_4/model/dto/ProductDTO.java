package com.example.project_sem_4.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer qty;

    private Integer rate;

    private String type;

    private Integer status;

    private CategoryDTO category;

    private RestaurantDTO restaurant;

    private Set<ImageDTO> images;
}
