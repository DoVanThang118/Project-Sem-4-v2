package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantReq {

    private Long id;

    private String name;

    private String description;

    private String tel;

    private List<String> meals;

    private List<String> cuisines;

    private Double rate;

    private LocalTime hourStart;

    private LocalTime hourEnd;

    private String address;

    private Integer status;

    //
    private Brand brand;
    private Long brandId;
    //
    private Set<Image> images;
    private Set<MultipartFile> img;
    //

    private int pageNumber = 0;

    private int pageSize = 20;
}
