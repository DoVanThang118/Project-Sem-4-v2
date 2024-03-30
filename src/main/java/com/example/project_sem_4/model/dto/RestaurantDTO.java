package com.example.project_sem_4.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;
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

    private List<String> meals;

    private List<String> cuisines;

    private Double rate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hourStart;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hourEnd;

    private String address;

    private Integer status;

    private BrandDTO brand;

    private Set<ImageDTO> images;
}
