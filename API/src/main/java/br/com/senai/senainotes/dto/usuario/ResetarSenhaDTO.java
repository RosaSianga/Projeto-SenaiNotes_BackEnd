package br.com.senai.senainotes.dto.usuario;

import lombok.Data;

@Data
public class ResetarSenhaDTO {

    // OPCIONAL - @NotBlank(message = "O campo e-mail é obrigatório.")
    // OPCIONAL - @Email(message = "Formato de e-mail inválido.")
    private String email;
}