package br.com.senai.senainotes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnotacaoCadastroDTO {

    private Integer idUsuario;
    private String titulo;
    private String descricao;
    private String urlImagem;
 }

