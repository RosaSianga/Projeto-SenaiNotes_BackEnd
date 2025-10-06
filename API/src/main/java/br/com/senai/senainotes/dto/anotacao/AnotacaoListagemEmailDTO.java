package br.com.senai.senainotes.dto.anotacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnotacaoListagemEmailDTO {

    private Integer idAnotacao;
    private String titulo;
    private String descricao;
    private String urlImagem;
    private Boolean flagArquivado;
    private String email;
}
