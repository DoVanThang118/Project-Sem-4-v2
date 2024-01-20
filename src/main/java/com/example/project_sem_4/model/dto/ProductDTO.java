package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
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

    private Category category;

    private Restaurant restaurant;

    private List<Gif> gifs = new ArrayList<>();
}
