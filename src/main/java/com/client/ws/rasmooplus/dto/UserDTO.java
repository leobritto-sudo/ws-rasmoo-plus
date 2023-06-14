package com.client.ws.rasmooplus.dto;

import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "O campo não deve ser em branco")
    @Size(min = 6, message = "Deve conter no mínimo 6 caracteres")
    private String name;

    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 11, message = "Deve conter no mínimo 11 dígitos")
    private String phone;

    @CPF(message = "CPF inválido")
    private String cpf;

    private LocalDateTime dtSubscription = LocalDateTime.now();

    private LocalDateTime dtExpiration = LocalDateTime.now();

    @NotNull
    private Long userTypeId;

    private Long subscriptionTypeId;
}
