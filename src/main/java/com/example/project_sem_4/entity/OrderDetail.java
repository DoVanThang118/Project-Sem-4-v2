package com.example.project_sem_4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer status;

    private Integer qty;

    private Double price;

    private Double total;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
}
