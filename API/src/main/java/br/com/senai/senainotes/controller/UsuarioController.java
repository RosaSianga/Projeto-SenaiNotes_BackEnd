package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuario = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/{id}")
    public ResponseEntity<Usuario> cadastrarUsuario (@RequestBody Usuario usuario) {

        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id) {

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário " + id + " não encontrado !");
        }

        return ResponseEntity.ok(usuario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario (@PathVariable Integer id) {

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " não encontrado !");
        }

        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario (@PathVariable Integer id, @RequestBody Usuario usuarioNovo) {

        Usuario usuarioAntigo = usuarioService.atualizarUsuario(id, usuarioNovo);
        if (usuarioAntigo == null) {
            return ResponseEntity.status(404).body("Usuario não encontrado !");
        }

        return ResponseEntity.ok(usuarioAntigo);
    }
}
