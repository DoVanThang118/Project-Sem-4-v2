package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReq {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    //
    private Set<Image> images;
    private List<Image> imagelist;
    private Set<MultipartFile> img;
    //

    private int pageNumber = 0;

    private int pageSize = 20;

}
