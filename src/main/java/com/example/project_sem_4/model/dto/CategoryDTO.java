package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    private Set<ImageDTO> images;
}
