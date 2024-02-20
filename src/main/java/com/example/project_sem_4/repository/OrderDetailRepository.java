package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "select o from OrderDetail o " +
            "WHERE (:id is null OR o.id in (:id)) " +
            "and (:status is null or o.status in (:status)) ")
    Page<OrderDetail> findOrderDetails(Pageable pageable, Long id, Integer status);
}
