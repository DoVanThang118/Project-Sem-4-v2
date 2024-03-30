package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Restaurant;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private Integer status;

    //
    private Set<MultipartFile> img;
    private Set<Image> images;
    //

    private int pageNumber = 0;

    private int pageSize = 20;

}
