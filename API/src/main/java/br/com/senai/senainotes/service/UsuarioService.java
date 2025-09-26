package br.com.senai.senainotes.service;

import br.com.senai.senainotes.models.Usuario;
import br.com.senai.senainotes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    public Usuario buscarPorId (Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario deletarUsuario (Integer id) {

        Usuario usuario = buscarPorId(id);
        if (usuario == null) {
            return null;
        }

        usuarioRepository.delete(usuario);
        return usuario;
    }


    public Usuario atualizarUsuario (Integer id, Usuario usuarioNovo) {

        Usuario usuarioAntigo = buscarPorId(id);
        if (usuarioAntigo == null) {
            return null;
        }

        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        usuarioAntigo.setSenha(usuarioNovo.getSenha());
        usuarioAntigo.setFlagDarkMode(usuarioNovo.getFlagDarkMode());
        return usuarioRepository.save(usuarioAntigo);
    }
}
