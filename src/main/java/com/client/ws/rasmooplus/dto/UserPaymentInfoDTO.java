package com.client.ws.rasmooplus.dto;

import com.client.ws.rasmooplus.model.User;
import jakarta.validation.constraints.NotNull;
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

    private String cardNumber;

    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    private String cardSecurityCode;

    private BigDecimal price;

    private LocalDateTime dtPayment = LocalDateTime.now();

    @NotNull(message = "Não deve ser nulo")
    private Long user;
}
