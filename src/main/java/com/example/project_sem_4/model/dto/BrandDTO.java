package com.example.project_sem_4.model.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private String description;

    private String hotline;

    private String email;

    private Integer status;

    private Set<ImageDTO> images;
}
