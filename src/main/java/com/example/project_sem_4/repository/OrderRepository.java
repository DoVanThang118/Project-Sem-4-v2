package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o join o.users u " +
            "WHERE (:id is null OR o.id in (:id)) " +
            "and (:userId is null OR u.id in (:id)) " +
            "and (:status is null or o.status in (:status)) ")
    Page<Order> findOrders(Pageable pageable, Long id, Long userId, Integer status);
}
