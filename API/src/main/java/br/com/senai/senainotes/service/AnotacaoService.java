package br.com.senai.senainotes.service;

import br.com.senai.senainotes.dto.AnotacaoCadastroDTO;
import br.com.senai.senainotes.dto.AnotacaoListagemDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.repository.AnotacaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnotacaoService {

    private final AnotacaoRepository anotacaoRepository;

    public AnotacaoService(AnotacaoRepository repo) {
        this.anotacaoRepository = repo;
    }

    //Busca as informações essenciais da lista de anotações
    public List<AnotacaoListagemDTO> listaTodasAnotacoes(){
        List<Anotacao> anotacoes = anotacaoRepository.findAll();
        return anotacoes.stream().map(this::converterListagem).
                collect(Collectors.toList());
    }

    public AnotacaoListagemDTO converterListagem(Anotacao anotacao){
        AnotacaoListagemDTO dto = new AnotacaoListagemDTO();

        dto.setIdAnotacao(anotacao.getId());
        dto.setTitulo(anotacao.getTitulo());
        dto.setDescricao(anotacao.getDescricao());
        dto.setUrlImagem(anotacao.getUrlImagem());
        dto.setFlagArquivado(anotacao.getFlagArquivado());
        dto.setIdUsuario(anotacao.getUsuario().getId());

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
    public Anotacao cadastrarAnotacao(Anotacao anotacao) {

        anotacao.setDataCriacao(OffsetDateTime.now());
        anotacao.setDataEdicao(OffsetDateTime.now());

        return anotacaoRepository.save(anotacao);
    }

    public AnotacaoCadastroDTO converterAnotacaoCadastro(Anotacao anotacao){
        AnotacaoCadastroDTO dto = new AnotacaoCadastroDTO();

        dto.setTitulo(anotacao.getTitulo());
        dto.setDescricao(anotacao.getDescricao());
        dto.setUrlImagem(anotacao.getUrlImagem());
        dto.setFlagArquivado(anotacao.getFlagArquivado());
        dto.setIdUsuario(anotacao.getUsuario().getId());

        return dto;
    }

    //Atualiza os dados da anotação
    public Anotacao atualizarAnotacao(Integer id, Anotacao anotacao) {
        Anotacao anotacaoExistente= ValidarAnotacao(id);
        if (anotacaoExistente == null) {
            return null;
        }

        anotacaoExistente.setTitulo(anotacao.getTitulo());
        anotacaoExistente.setDescricao(anotacao.getDescricao());
        anotacaoExistente.setDataEdicao(OffsetDateTime.now());
        anotacaoExistente.setUrlImagem(anotacao.getUrlImagem());
        anotacaoExistente.setFlagArquivado(anotacao.getFlagArquivado());

        return anotacaoRepository.save(anotacaoExistente);
    }

    //Deleta a anotação
    public Anotacao deletarAnotacao(Integer id) {
        Anotacao anotacaoExistente= ValidarAnotacao(id);
        if (anotacaoExistente == null) {
            return null;
        }
        anotacaoRepository.delete(anotacaoExistente);
        return anotacaoExistente;
    }

}
