package br.com.senai.senainotes.config;

import br.com.senai.senainotes.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername (String usuario) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado !"));
    }
}
