package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    @Email(message = "Inválido")
    private String email;

    @NotBlank(message = "Atribuo não pode ser vazio")
    private String password;

    private String recoveryCode;
}
