package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.Restaurant;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandReq {

    private Long id;

    private String name;

    private String description;

    private String hotline;

    private String email;

    private List<Restaurant> restaurants = new ArrayList<>();
    //
    private List<Gif> gifs = new ArrayList<>();
    private List<MultipartFile> img;
    //
    private int pageNumber = 0;

    private int pageSize = 20;

}
