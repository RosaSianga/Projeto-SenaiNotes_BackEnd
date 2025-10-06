package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.login.LoginDTO;
import br.com.senai.senainotes.dto.configuracao.FlagDarkModeDTO;
import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar{id}")
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuario = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario (@RequestBody LoginDTO usuario) {

        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }


    @GetMapping("/buscar{id}")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id) {

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário " + id + " não encontrado !");
        }

        return ResponseEntity.ok(usuario);
    }


    @DeleteMapping("/deletar{id}")
    public ResponseEntity<?> deletarUsuario (@PathVariable Integer id) {

        Usuario usuario = usuarioService.deletarUsurio(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " não encontrado !");
        }

        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/atualizar{id}")
    public ResponseEntity<?> atualizarUsuario (@PathVariable Integer id, @RequestBody Usuario usuarioNovo) {

        Usuario usuarioAntigo = usuarioService.atualizarUsuario(id, usuarioNovo);
        if (usuarioAntigo == null) {
            return ResponseEntity.status(404).body("Usuario não encontrado !");
        }

        return ResponseEntity.ok(usuarioAntigo);
    }


    @PutMapping("/config{id}")
    public ResponseEntity<Usuario> alterarDarkMode (@PathVariable Integer id, @RequestBody FlagDarkModeDTO novoDarkMode) {

        Usuario atualMode = usuarioService.alterarDarkMode(id, novoDarkMode);
        if (atualMode == null) {
            return null;
        }

        return ResponseEntity.ok(atualMode);
    }

}
