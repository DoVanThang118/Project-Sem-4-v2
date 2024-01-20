package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    private List<Product> products = new ArrayList<>();

    private List<Gif> gifs = new ArrayList<>();
}
