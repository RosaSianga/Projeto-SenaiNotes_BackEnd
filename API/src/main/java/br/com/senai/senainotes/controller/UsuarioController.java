package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.usuario.UsuarioListagemDto;
import br.com.senai.senainotes.dto.login.LoginDTO;
import br.com.senai.senainotes.dto.configuracao.FlagDarkModeDTO;
import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuário", description = "Operações relacionadas ao usuário")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    // Dto Listar
    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar() {
        List<UsuarioListagemDto> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/listar")
    @Operation( summary = "Listar todos os usuários do sistema")
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuario = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/cadastrar")
    @Operation( summary = "Cadastrar um usuário no sistema")
    public ResponseEntity<Usuario> cadastrarUsuario (@RequestBody LoginDTO usuario) {

        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }


    @GetMapping("/{id}")
    @Operation( summary = "Pesquisar o usuário por ID")
    public ResponseEntity<UsuarioListagemDto> listarUsuariosPorId (@PathVariable Integer id) {
        UsuarioListagemDto usuarios = usuarioService.buscarUsuarioPorId(id);
        if (usuarios == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(usuarios);
    }


    @DeleteMapping("/deletar")
    @Operation( summary = "Deletar o usuário do sistema")
    public ResponseEntity<?> deletarUsuario (@PathVariable Integer id) {

        Usuario usuario = usuarioService.deletarUsurio(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " não encontrado !");
        }

        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/atualizar")
    @Operation( summary = "Atualizar dados do usuário no sistema")
    public ResponseEntity<?> atualizarUsuario (@PathVariable Integer id, @RequestBody Usuario usuarioNovo) {

        Usuario usuarioAntigo = usuarioService.atualizarUsuario(id, usuarioNovo);
        if (usuarioAntigo == null) {
            return ResponseEntity.status(404).body("Usuario não encontrado !");
        }

        return ResponseEntity.ok(usuarioAntigo);
    }


    @PutMapping("/darkmode")
    @Operation( summary = "Alterar modo de visualização do usuário no sistema (modo escuro)")
    public ResponseEntity<Usuario> alterarDarkMode (@PathVariable Integer id, @RequestBody FlagDarkModeDTO novoDarkMode) {

        Usuario atualMode = usuarioService.alterarDarkMode(id, novoDarkMode);
        if (atualMode == null) {
            return null;
        }

        return ResponseEntity.ok(atualMode);
    }

}
