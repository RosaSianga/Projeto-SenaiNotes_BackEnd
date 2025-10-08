package br.com.senai.senainotes.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank (message = "O e-mail não pode estar em branco.")
    @Email (message = "O formato do e-mail é inválido.")
    private String email;

    @NotBlank (message = "A senha não pode estar em branco.")
    @Size (min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;
}
