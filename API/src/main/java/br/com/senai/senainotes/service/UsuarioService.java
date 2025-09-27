package br.com.senai.senainotes.service;

import br.com.senai.senainotes.dto.CadastroDTO;
import br.com.senai.senainotes.dto.FlagDarkModeDTO;
import br.com.senai.senainotes.model.Usuario;
import br.com.senai.senainotes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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


    public Usuario criarUsuario (CadastroDTO dto) {

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


    public Usuario flagDarkMode (FlagDarkModeDTO dto, Integer id) {

        Usuario modoDark = usuarioRepository.findById(dto.getId()).orElse(null);
        if (modoDark == null) {
            return null;
        }

        Usuario flag = new Usuario();

        flag.setFlagDarkMode(dto.getFlagDarkMode());
        return usuarioRepository.save(flag);
    }
}
