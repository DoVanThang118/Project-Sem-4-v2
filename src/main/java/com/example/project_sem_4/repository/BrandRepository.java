package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
//    @Query(value = "select b from Brand b " +
//            "where (coalesce(:name, null) is null or b.name like %:name%) " +
//            "and (coalesce(:description, null) is null or b.description like %:description%) " +
//            "and (coalesce(:hotline, null) is null or b.hotline like %:hotline%) " +
//            "and (coalesce(:email, null) is null or b.email like %:email%) "
//    )
    @Query(value = "SELECT b FROM Brand b " +
            "WHERE (:id is null OR b.id in (:id)) " +
            "AND (:name IS NULL OR b.name LIKE %:name%) " +
            "AND (:description IS NULL OR b.description LIKE %:description%) " +
            "AND (:hotline IS NULL OR b.hotline LIKE %:hotline%) " +
            "AND (:email IS NULL OR b.email LIKE %:email%)")
    Page<Brand> findBrands(Pageable pageable,Long id, String name, String description, String hotline, String email);

    Brand findByName(String name);
}
