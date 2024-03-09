package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    @Query(value = "select u from User u " +
            "WHERE (:id is null OR u.id in (:id)) " +
            "and (:name is null or u.name like %:name%) " +
            "and (:email is null or u.email like %:email%) " +
            "and (:tel is null or u.tel like %:tel%) " +
            "and (:address is null or u.address like %:address%) " +
            "and (:birthday is null or u.birthday = :birthday) " +
            "and (:type is null or u.type like %:type%) " +
            "and (:status is null or u.status in (:status)) "
    )
    Page<User> findUsers(Pageable pageable, Long id, String name, String email, String tel, String address, LocalDate birthday, String type, Integer status);
}
