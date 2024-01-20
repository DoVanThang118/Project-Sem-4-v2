package com.example.project_sem_4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String tel;

    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    private String type;

    private Integer status;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cart> carts = new ArrayList<>() ;

    public void addCart(Cart cart) {
        carts.add(cart);
        cart.setUser(this);
    }

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderDetail> orderDetails = new ArrayList<>() ;

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setUser(this);
    }

    public void deleteCart(Cart cart) {
        carts.remove(cart);
        cart.setUser(null);
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Gif> gifs = new ArrayList<>();

    public void addGif(Gif gif){
        gifs.add(gif);
        gif.setUser(this);
    }

    public void deleteGif(Gif gif){
        gifs.remove(gif);
        gif.setUser(null);
    }

}
