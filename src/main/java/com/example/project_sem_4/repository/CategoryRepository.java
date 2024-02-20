package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query(value = "select c from Category c " +
            "WHERE (:id is null OR c.id in (:id)) " +
            "and (:name is null or c.name like %:name%) " +
            "and (:description is null or c.description like %:description%) " +
            "and (:status is null or c.status in (:status)) "
    )
    Page<Category> findCategories(Pageable pageable, Long id, String name, String description, Integer status);
}
