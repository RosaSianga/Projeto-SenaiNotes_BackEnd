package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.login.LoginDTO;
import br.com.senai.senainotes.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;

    public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> login (@RequestBody LoginDTO cadastroUsuarioDTO) {

        var cadastroToken= new UsernamePasswordAuthenticationToken(cadastroUsuarioDTO.getEmail(), cadastroUsuarioDTO.getSenha());

        Authentication login = authenticationManager.authenticate(cadastroToken);

        Instant now = Instant.now();
        long validade = 3600L;


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senainotes-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(validade))
                .subject(login.getName())
                .claim("roles", login.getAuthorities())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String token= this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return ResponseEntity.ok(token);
    }
}








