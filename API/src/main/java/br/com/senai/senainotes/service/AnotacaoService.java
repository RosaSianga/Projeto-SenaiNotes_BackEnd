package br.com.senai.senainotes.service;

import br.com.senai.senainotes.dto.anotacao.AnotacaoArquivadaDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoCadastroDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemEmailDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.model.Nota;
import br.com.senai.senainotes.model.Tag;
import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.repository.AnotacaoRepository;
import br.com.senai.senainotes.repository.TagRepository;
import br.com.senai.senainotes.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnotacaoService {

    private final AnotacaoRepository anotacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final NotaService notaService;

    public AnotacaoService(AnotacaoRepository repo, UsuarioRepository usuarioRepository, TagRepository tagRepository, TagService tagService, NotaService notaService) {
        this.anotacaoRepository = repo;
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.notaService = notaService;
    }

    //Busca as informações essenciais da lista de anotações
    public List<AnotacaoListagemDTO> listaTodasAnotacoes() {
        List<Anotacao> anotacoes = anotacaoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return anotacoes.stream().map(this::converterListagem).
                collect(Collectors.toList());
    }

    public List<AnotacaoListagemDTO> listaTodasAnotacoesPorUsuario(Integer idUsuario) {
        List<Anotacao> anotacoes = anotacaoRepository.findByUsuarioId(idUsuario);
        return anotacoes.stream().map(this::converterListagem).
                collect(Collectors.toList());
    }

    public AnotacaoListagemDTO converterListagem(Anotacao anotacao) {
        AnotacaoListagemDTO dto = new AnotacaoListagemDTO();

        dto.setIdAnotacao(anotacao.getId());
        dto.setTitulo(anotacao.getTitulo());
        dto.setDescricao(anotacao.getDescricao());
        dto.setUrlImagem(anotacao.getUrlImagem());
        dto.setFlagArquivado(anotacao.getFlagArquivado());
        dto.setIdUsuario(anotacao.getUsuario().getId());

        return dto;
    }

    public List<AnotacaoListagemEmailDTO> listaTodasAnotacoesPorEmail(String email) {
        List<Anotacao> anotacoes = anotacaoRepository.findByUsuarioEmail(email);
        return anotacoes.stream().map(this::converterListagemEmail).
                collect(Collectors.toList());
    }

    public AnotacaoListagemEmailDTO converterListagemEmail(Anotacao anotacao) {
        AnotacaoListagemEmailDTO dto = new AnotacaoListagemEmailDTO();

        dto.setIdAnotacao(anotacao.getId());
        dto.setTitulo(anotacao.getTitulo());
        dto.setDescricao(anotacao.getDescricao());
        dto.setUrlImagem(anotacao.getUrlImagem());
        dto.setFlagArquivado(anotacao.getFlagArquivado());
        dto.setEmail(anotacao.getUsuario().getEmail());

        return dto;
    }

    //Busca as informações essenciais da anotação pelo ID
    public AnotacaoListagemDTO buscarAnotacaoPorId(Integer id) {
        Anotacao anotacao = anotacaoRepository.findById(id).orElse(null);
        return converterListagem(anotacao);
    }

    //Consulta se a anotação existe para atualizar ou deletar
    public Anotacao ValidarAnotacao(Integer id) {
        return anotacaoRepository.findById(id).orElse(null);
    }

    //Cadastra uma nova anotação
    public Anotacao cadastrarAnotacao(AnotacaoCadastroDTO dto) {
        Usuario usuarioAssociado = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);

        if (usuarioAssociado == null) {
            return null;
        }

        Anotacao novaAnotacao = new Anotacao();

        novaAnotacao.setDataCriacao(OffsetDateTime.now());
        novaAnotacao.setDataEdicao(OffsetDateTime.now());
        novaAnotacao.setTitulo(dto.getTitulo());
        novaAnotacao.setDescricao(dto.getDescricao());
        novaAnotacao.setUrlImagem(dto.getUrlImagem());
        novaAnotacao.setUsuario(usuarioAssociado);

        Anotacao anotacao = anotacaoRepository.save(novaAnotacao);

//Consultar se a Tag existe para recuperar o id ou criar nova

        for (int i = 0; i < dto.getTags().size(); i++) {

            br.com.senai.senainotes.model.Tag tag = tagRepository.findByNome(dto.getTags().get(i));
            if (tag == null) {
                Tag novaTag = new Tag();
                novaTag.setNome(dto.getTags().get(i));
                tagService.cadastrarTag(novaTag);
                tag.setId(novaTag.getId());
            }

            // cadastrar a tabela itermediaria com o tag.ig e a.id
            Nota nota = new Nota();

            nota.setIdTag(tag);
            nota.setIdAnotacao(anotacao);

            notaService.cadastrarNota(nota);
        }

        return novaAnotacao;
    }


    //Atualiza os dados da anotação
    public Anotacao atualizarAnotacao(Integer id, AnotacaoCadastroDTO anotacao) {
        Anotacao anotacaoExistente = ValidarAnotacao(id);
        if (anotacaoExistente == null) {
            return null;
        }

        anotacaoExistente.setTitulo(anotacao.getTitulo());
        anotacaoExistente.setDescricao(anotacao.getDescricao());
        anotacaoExistente.setDataEdicao(OffsetDateTime.now());
        anotacaoExistente.setUrlImagem(anotacao.getUrlImagem());

        return anotacaoRepository.save(anotacaoExistente);
    }

    //Atualizar a nota para Arquivado
    public Anotacao arquivarNota(Integer id, AnotacaoArquivadaDTO dto) {
        Anotacao anotacaoExistente = ValidarAnotacao(id);
        if (anotacaoExistente == null) {
            return null;
        }
        anotacaoExistente.setFlagArquivado(dto.getFlagArquivado());
        anotacaoExistente.setDataEdicao(OffsetDateTime.now());

        return anotacaoRepository.save(anotacaoExistente);
    }

    //Deleta a anotação
    public Anotacao deletarAnotacao(Integer id) {
        Anotacao anotacaoExistente = ValidarAnotacao(id);
        if (anotacaoExistente == null) {
            return null;
        }
        anotacaoRepository.delete(anotacaoExistente);
        return anotacaoExistente;
    }

}
