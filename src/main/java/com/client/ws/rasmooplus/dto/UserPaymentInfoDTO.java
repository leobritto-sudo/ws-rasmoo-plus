package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentInfoDTO {
    private Long id;

    @Size(min = 16, max = 16, message = "Deve conter 16 caracteres")
    private String cardNumber;

    @Min(1)
    @Max(12)
    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    @Size(min = 3, max = 3, message = "Deve conter 3 números")
    private String cardSecurityCode;

    private BigDecimal price;

    private LocalDateTime dtPayment = LocalDateTime.now();

    private Integer installments;

    @NotNull(message = "Não deve ser nulo")
    private Long userId;
}
