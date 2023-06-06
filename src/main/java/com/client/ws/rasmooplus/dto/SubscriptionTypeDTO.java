package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionTypeDTO {
    private Long id;

    @NotBlank(message = "Não deve ser em branco")
    @Size(min = 5, max = 30, message = "Deve ter no mínimo 5 e máximo 30 caracteres")
    private String name;

    @Max(value = 12, message = "Deve conter um mês válido")
    private Long accessMonth;

    @NotNull(message = "Não deve ser nulo")
    private BigDecimal price;

    @NotBlank(message = "Não deve ser em branco")
    @Size(min = 5, max = 15, message = "Deve ter no mínimo 5 e máximo 15 caracteres")
    private String productKey;
}
