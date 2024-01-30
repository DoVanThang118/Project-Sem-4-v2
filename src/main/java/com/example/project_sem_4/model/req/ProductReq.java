package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


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
    private String nameCategory;
    //
    private Restaurant restaurant;
    private Long restaurantId;
    //
    private List<Gif> gifs = new ArrayList<>();
    private List<MultipartFile> img;
    //
    private int pageNumber = 0;

    private int pageSize = 20;
}
