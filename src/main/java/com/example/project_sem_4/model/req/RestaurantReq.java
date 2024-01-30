package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Product;
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
public class RestaurantReq {

    private Long id;

    private String name;

    private String description;

    private String tel;

    private String address;

    private Integer status;

    //
    private Brand brand;
    private Long brandId;
    //

    private List<Product> products = new ArrayList<>();

    //
    private List<Gif> gifs = new ArrayList<>();
    private List<MultipartFile> img;
    //

    private int pageNumber = 0;

    private int pageSize = 20;
}
