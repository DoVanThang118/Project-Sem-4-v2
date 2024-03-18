package com.example.project_sem_4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "total_money")
    private Double totalMoney;

    private Integer status;

    private String note;

    private String name;

    private String email;

    private String phone;

    private String address;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_users",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    private List<OrderDetail> orderDetails = new ArrayList<>();

}
