package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    
    @Query(value = "select c from Cart c " +
            "where (coalesce(:status, null) is null or c.status in (:status)) ")
    Page<Cart> findCarts(Pageable pageable, Integer status);


    List<Cart> findByUser(User user);
}
