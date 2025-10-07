package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.tag.TagListagemDTO;
import br.com.senai.senainotes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tags", description = "Operações relacionadas as tags")
public class TagController {
    private TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Listar
    @GetMapping
    @Operation(summary = "Lista todos as Tags", description = "Retorna uma lista com todas as tags cadastradas")
    @ApiResponse(responseCode = "200", description = "Operacao bem-sucedida")
    public ResponseEntity<List<TagListagemDTO>> ListarTags() {
        List<TagListagemDTO> tags = tagService.listarTags();
        return ResponseEntity.ok().body(tags);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Busca tags por ID", description = "Retorna uma tag especifica com base no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag nao encontrado para o ID informado")
    })
    public ResponseEntity<TagListagemDTO> buscarTagPorId(@PathVariable Integer id) {
        TagListagemDTO tag = tagService.buscarTagPorId(id);

        if (tag == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(tag);
    }

    // Cadastrar
    @PostMapping
    @Operation(summary = "Cadastrar uma nova Tag", description = "Adiciona uma nova tag ao banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos fornecidos para o cadastro")
    })
    public ResponseEntity<br.com.senai.senainotes.model.Tag> cadastrarTag(@RequestBody br.com.senai.senainotes.model.Tag tag) {
        br.com.senai.senainotes.model.Tag tagNova = tagService.cadastrarTag(tag);

        if (tagNova == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagNova);
    }

    // Atualizar
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tag existente", description = "Altera os dados de uma tag com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarTag(@PathVariable Integer id, @RequestBody br.com.senai.senainotes.model.Tag tag) {
        br.com.senai.senainotes.model.Tag tagAtualizada = tagService.atualizaTag(id, tag);

        if (tag == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrado");
        }
        return ResponseEntity.ok(tagAtualizada);
    }

    // Deletar
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tag", description = "Remove uma tag do banco de dados com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag excluído com sucesso"),
            @ApiResponse(responseCode = "404", description =  "Tag não encontrado para o ID informado")
    })
    public ResponseEntity<?> deletarTag(@PathVariable Integer id) {
        br.com.senai.senainotes.model.Tag deletado = tagService.deletarTag(id);

        if (deletado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possivel excluir, pois a tag não foi encontrado");
        }
        return ResponseEntity.ok("Tag deletado com sucesso");
    }
}
