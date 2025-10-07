package br.com.senai.senainotes.dto.anotacao;

import br.com.senai.senainotes.dto.tag.TagListagemDTO;
import br.com.senai.senainotes.model.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnotacaoListagemDTO {

    private Integer idAnotacao;
    private String titulo;
    private String descricao;
    private String urlImagem;
    private Boolean flagArquivado;
    private Integer idUsuario;
    private List<TagListagemDTO> tags;
}
