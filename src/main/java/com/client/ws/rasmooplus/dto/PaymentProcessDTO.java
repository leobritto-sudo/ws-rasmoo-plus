package com.client.ws.rasmooplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessDTO {
    @NotNull(message = "Não deve ser nulo")
    private String productKey;

    private BigDecimal discount;

    @NotNull(message = "As informações do pagamento devem ser informadas")
    @JsonProperty("userPaymentInfo")
    private UserPaymentInfoDTO userPaymentInfoDTO;
}
