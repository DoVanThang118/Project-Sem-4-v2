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
            "where (coalesce(:name, null) is null or p.name like %:name%) " +
            "and (coalesce(:description, null) is null or p.description like %:description%) " +
            "and (coalesce(:price, null) is null or p.price in (:price)) " +
            "and (coalesce(:status, null) is null or p.status in (:status)) " +
            "and (coalesce(:type, null) is null or p.type like %:type%) " +
            "and (coalesce(:rate, null) is null or p.rate in (:rate)) " +
            "and (coalesce(:category, null) is null or c.name like %:category%) "
    )
    Page<Product> findProducts(Pageable pageable, String name, String description, Double price, Integer status, String type, Integer rate, String category);
}
