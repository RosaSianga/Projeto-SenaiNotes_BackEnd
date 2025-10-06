package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.anotacao.AnotacaoArquivadaDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoCadastroDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemEmailDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.service.AnotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anotacao")
@Tag(name = "Controle de Anotações", description = "Operações relacionadas a anotações")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    public AnotacaoController(AnotacaoService service) {
        anotacaoService = service;
    }

    @GetMapping
    @Operation(
            summary = "Listar todas anotações do sistema"
    )
    public ResponseEntity<List<AnotacaoListagemDTO>> listarAnotacoes() {
        List<AnotacaoListagemDTO> anotacoes = anotacaoService.listaTodasAnotacoes();
        return ResponseEntity.ok(anotacoes);
    }

    @GetMapping("/porUsuario/{idUsuario}")
    @Operation(
            summary = "Listar as anotações pelo ID usuário"
    )
    public ResponseEntity<List<AnotacaoListagemDTO>> listarAnotacoesPorUsuario(@PathVariable Integer idUsuario) {
        List<AnotacaoListagemDTO> anotacoesUsuario = anotacaoService.listaTodasAnotacoesPorUsuario(idUsuario);
        return ResponseEntity.ok(anotacoesUsuario);
    }

    @GetMapping("/porEmail/{email}")
    @Operation(
            summary = "Listar as anotações pelo Email"
    )
    public ResponseEntity<List<AnotacaoListagemEmailDTO>> listarAnotacoesPorEmail(@PathVariable String email) {
        List<AnotacaoListagemEmailDTO> anotacoesEmail = anotacaoService.listaTodasAnotacoesPorEmail(email);
        return ResponseEntity.ok(anotacoesEmail);
    }


    @GetMapping("/porId/{idNota}")
    @Operation(
            summary = "Lista a anotação pelo ID da nota"
    )
    public ResponseEntity<?> listarAnotacoesPorId(@PathVariable Integer id) {
        AnotacaoListagemDTO anotacao = anotacaoService.buscarAnotacaoPorId(id);
        if (anotacao == null) {
            return ResponseEntity.badRequest().body("Anotação não encontrada");
        }
        return ResponseEntity.ok(anotacao);
    }

    @PostMapping
    @Operation(
            summary = "Cadatrar uma nota nova"
    )
    public ResponseEntity<Anotacao> cadastrarAnotacao(@RequestBody AnotacaoCadastroDTO dto) {
        Anotacao anotacao = anotacaoService.cadastrarAnotacao(dto);
        if  (anotacao == null) {
            return ResponseEntity.badRequest().body(anotacao);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(anotacao);
    }

    @PutMapping("/arquivarNota/{idNota}")
    @Operation(
            summary = "Arquiva a nota pelo ID da nota"
    )
    public ResponseEntity <?> arquivarNota(@PathVariable Integer id, @RequestBody AnotacaoArquivadaDTO dto) {
        Anotacao anotacaoExistente = anotacaoService.arquivarNota(id, dto);
        if (anotacaoExistente == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok(anotacaoExistente);
    }

    @PutMapping("/desarquivarNota/{idNota}")
    @Operation(
            summary = "Desarquivar a nota pelo ID da nota"
    )
    public ResponseEntity <?> desarquivarNota(@PathVariable Integer id, @RequestBody AnotacaoArquivadaDTO dto) {
        Anotacao anotacaoExistente = anotacaoService.arquivarNota(id, dto);
        if (anotacaoExistente == null) {
            return ResponseEntity.badRequest().body("Nota não encontrada");
        }
        return ResponseEntity.ok(anotacaoExistente);
    }

}
