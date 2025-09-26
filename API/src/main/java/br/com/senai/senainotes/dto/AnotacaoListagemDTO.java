package br.com.senai.senainotes.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AnotacaoListagemDTO {

    private Integer idAnotacao;
    private String titulo;
    private String descricao;
    private String urlImagem;
    private Boolean flagArquivado;
    private Integer idUsuario;
}
