package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.anotacao.AnotacaoArquivadaDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoCadastroDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemDTO;
import br.com.senai.senainotes.dto.anotacao.AnotacaoListagemEmailDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.service.AnotacaoService;
import br.com.senai.senainotes.service.ArmazenamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/anotacao")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Controle de Anotações", description = "Operações relacionadas a anotações")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;
    private final ArmazenamentoService armazenamentoService;

    public AnotacaoController(AnotacaoService service, ArmazenamentoService armazenamentoService) {
        anotacaoService = service;
        this.armazenamentoService = armazenamentoService;
    }

    @GetMapping
    @Operation(
            summary = "Listar todas anotações do sistema"
    )
    public ResponseEntity<List<AnotacaoListagemDTO>> listarAnotacoes() {
        List<AnotacaoListagemDTO> anotacoes = anotacaoService.listaTodasAnotacoes();
        return ResponseEntity.ok(anotacoes);
    }

    @GetMapping("/consultaPorUsuario/{idUsuario}")
    @Operation(
            summary = "Listar as anotações pelo ID usuário"
    )
    public ResponseEntity<List<AnotacaoListagemDTO>> listarAnotacoesPorUsuario(@PathVariable Integer idUsuario) {
        List<AnotacaoListagemDTO> anotacoesUsuario = anotacaoService.listaTodasAnotacoesPorUsuario(idUsuario);
        return ResponseEntity.ok(anotacoesUsuario);
    }

    @GetMapping("/consultaPorEmail/{email}")
    @Operation(
            summary = "Listar as anotações pelo Email"
    )
    public ResponseEntity<List<AnotacaoListagemEmailDTO>> listarAnotacoesPorEmail(@PathVariable String email) {
        List<AnotacaoListagemEmailDTO> anotacoesEmail = anotacaoService.listaTodasAnotacoesPorEmail(email);
        return ResponseEntity.ok(anotacoesEmail);
    }


    @GetMapping("/consultaPorId/{idAnotacao}")
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

    @PostMapping("/cadastrar")
    @Operation(
            summary = "Cadatrar uma nota nova"
    )
    public ResponseEntity<Anotacao> cadastrarAnotacao(@RequestBody AnotacaoCadastroDTO dto) {
        Anotacao anotacao = anotacaoService.cadastrarAnotacao(dto);
        if  (anotacao == null) {
            return ResponseEntity.badRequest().body(anotacao);
        }
        anotacao.setUsuario(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(anotacao);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/cadastrar/imagem")
    @Operation(
            summary = "Cadatrar uma nota nova com Imagem"
    )
    public ResponseEntity<Anotacao> cadastrarAnotacaoComImagem(@ParameterObject @ModelAttribute AnotacaoCadastroDTO dto, @RequestPart("imagem") MultipartFile imagem) {
        // 1. Salva o arquivo no disco usando nosso serviço e pega o nome único gerado.
        String nomeArquivo = armazenamentoService.salvarArquivo(imagem);

        // 2. Guarda o nome do arquivo no DTO para ser salvo no banco de dados.
        dto.setUrlImagem(nomeArquivo);

        Anotacao anotacao = anotacaoService.cadastrarAnotacao(dto);
        if  (anotacao == null) {
            return ResponseEntity.badRequest().body(anotacao);
        }
        anotacao.setUsuario(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(anotacao);
    }

    @GetMapping("/imagens/{nomeDoArquivo}")
    public ResponseEntity<Resource> servirImagem(@PathVariable String nomeDoArquivo) {
        // 1. Carrega o arquivo do disco usando nosso serviço.
        Resource arquivo = armazenamentoService.carregarArquivo(nomeDoArquivo);

        // 2. Retorna o arquivo como resposta. O header "Content-Type" informa
        // ao navegador que ele está recebendo uma imagem e deve exibi-la.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(arquivo);
    }

    @PutMapping("/arquivarNota/{id}")
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

    @PutMapping("/desarquivarNota/{id}")
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
