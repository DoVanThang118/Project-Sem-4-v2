package com.example.project_sem_4.model.req;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayReq {

    private Long amount;
    private String description;
    @Pattern(regexp = "^(VNPay|Paypal)$", message = "Invalid payment method. Allowed values are 'VNPay' or 'Paypal'")
    private String paymentMethod;
    private Long programId;
    private Long userId;
}
