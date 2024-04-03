package com.example.project_sem_4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDTO {

    private Integer totalOrder;

    private Double totalRevenue;
}
