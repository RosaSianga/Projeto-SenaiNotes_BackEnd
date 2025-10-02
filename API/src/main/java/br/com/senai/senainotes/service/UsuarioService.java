package br.com.senai.senainotes.service;

import br.com.senai.senainotes.dto.login.CadastroUsuarioDTO;
import br.com.senai.senainotes.dto.configuracao.FlagDarkModeDTO;
import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    public Usuario criarUsuario (CadastroUsuarioDTO dto) {

        Usuario novoUsuario = new Usuario();

        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(dto.getSenha());
        novoUsuario.setDataCadastro(OffsetDateTime.now());
        return usuarioRepository.save(novoUsuario);
    }


    public Usuario buscarPorId (Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario deletarUsurio (Integer id) {

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


    public Usuario alterarDarkMode (Integer id, FlagDarkModeDTO dto) {

        Usuario modoDark = buscarPorId(id);
        if (modoDark == null) {
            return null;
        }

        modoDark.setFlagDarkMode(dto.getFlagDarkMode());
        return usuarioRepository.save(modoDark);
    }
}
