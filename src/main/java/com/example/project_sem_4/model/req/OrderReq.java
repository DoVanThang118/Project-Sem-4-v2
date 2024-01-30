package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.OrderDetail;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq {

    private Long id;

    private Date createDate;

    private Double totalMoney;

    private Integer status;

    private String note;

    private String address;

    private String phone;

    private Set<User> users;

    //
    private List<Long> orderDetailId;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    //

    private int pageNumber = 0;

    private int pageSize = 20;
}
