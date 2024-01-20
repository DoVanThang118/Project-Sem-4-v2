package com.example.project_sem_4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name.toUpperCase(Locale.ROOT);
    }

    public void setDescription(String description) {
        this.description = description.toUpperCase(Locale.ROOT);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
