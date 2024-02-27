package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Image;
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
public class ImageReq {

    private Long id;

    private String title;

    private String url;

    private Integer status;

    //
    private List<Image> images = new ArrayList<>();
    private List<MultipartFile> img;
    //
    private int pageNumber = 0;

    private int pageSize = 20;
}
