package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private String description;

    private String hotline;

    private String email;

    private List<Restaurant> restaurants;

    private List<Gif> gifs;
}
