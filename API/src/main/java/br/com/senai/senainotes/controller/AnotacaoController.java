package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.AnotacaoListagemDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.service.AnotacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<AnotacaoListagemDTO>> listarAnotacoes() {
        List<AnotacaoListagemDTO> anotacoes = anotacaoService.listaTodasAnotacoes();
        return ResponseEntity.ok(anotacoes);
    }


    @GetMapping("/id")
    public ResponseEntity<AnotacaoListagemDTO> listarAnotacoesPorId(@PathVariable Integer id) {
        AnotacaoListagemDTO anotacao = anotacaoService.buscarAnotacaoPorId(id);
        if (anotacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(anotacao);
    }

//    @PostMapping
//    public ResponseEntity<AnotacaoListagemDTO> cadastrarAnotacao(@RequestBody Anotacao anotacao) {
//
//

}
