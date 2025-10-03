package br.com.senai.senainotes.dto.anotacao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnotacaoCadastroDTO {

    private Integer idUsuario;
    private String titulo;
    private String descricao;
    private String urlImagem;
    private List<String> tags;

 }

