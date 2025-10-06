package br.com.senai.senainotes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter
public class UsuarioListagemDto {
    private  Integer id;
    private String email;
    private OffsetDateTime dataCadastro;
}
