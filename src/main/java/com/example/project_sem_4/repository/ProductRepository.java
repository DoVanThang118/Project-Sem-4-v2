package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
    @Query(value = "select p from Product p join p.category c " +
            "WHERE (:id is null OR p.id in (:id)) " +
            "and (:name is null or p.name like %:name%) " +
            "and (:description is null or p.description like %:description%) " +
            "and (:price is null or p.price in (:price)) " +
            "and (:status is null or p.status in (:status)) " +
            "and (:type is null or p.type like %:type%) " +
            "and (:rate is null or p.rate in (:rate)) " +
            "and (:categoryId is null or c.id in (:categoryId)) "
    )
    Page<Product> findProducts(Pageable pageable, Long id, String name, String description, Double price, Integer status, String type, Integer rate, Long categoryId);
}
