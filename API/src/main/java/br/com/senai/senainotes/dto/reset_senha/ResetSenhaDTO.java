package br.com.senai.senainotes.dto.reset_senha;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetSenhaDTO {

    @NotBlank(message = "O campo e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    public String email;
}
