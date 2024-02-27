package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Restaurant;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer qty;

    private Integer rate;

    private String type;

    private Integer status;
    //
    private Category category;
    private Long categoryId;
    //
    private Restaurant restaurant;
    private Long restaurantId;
    //
    private Set<Image> images;
    private Set<MultipartFile> img;
    //
    private int pageNumber = 0;

    private int pageSize = 20;
}
