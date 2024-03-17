package com.example.project_sem_4.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long id;

    private String title;

    private String url;

    private Integer status;
}
