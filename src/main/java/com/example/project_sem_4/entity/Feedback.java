package com.example.project_sem_4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "create_date")
    private Date createDate;

    private Integer rate;

    private Integer status;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "feedbacks_images",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "feedbacks_users",
            joinColumns = @JoinColumn(name = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    private Order order;
}
