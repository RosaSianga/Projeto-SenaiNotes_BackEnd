package br.com.senai.senainotes.dto.anotacao;

import br.com.senai.senainotes.dto.tag.TagListagemDTO;
import br.com.senai.senainotes.model.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnotacaoListagemEmailDTO {

    private Integer idAnotacao;
    private String titulo;
    private String descricao;
    private String urlImagem;
    private Boolean flagArquivado;
    private Integer idUsuario;
    private String email;
    private List<TagListagemDTO> tags;
    private Integer id_tags;
    private String nome_tags;
    private String usuario;
}
