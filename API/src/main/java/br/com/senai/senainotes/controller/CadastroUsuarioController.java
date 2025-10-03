package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.dto.login.CadastroUsuarioDTO;
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
@RequestMapping("/api/cadastro")
public class CadastroUsuarioController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public CadastroUsuarioController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping
    public ResponseEntity<?> cadastro (@RequestBody CadastroUsuarioDTO cadastroUsuarioDTO) {

        var cadastroToken= new UsernamePasswordAuthenticationToken(cadastroUsuarioDTO.getEmail(), cadastroUsuarioDTO.getSenha());

        Authentication cadastro = authenticationManager.authenticate(cadastroToken);

        Instant now = Instant.now();
        long validade = 3600L;


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senainotes-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(validade))
                .subject(cadastro.getName())
                .claim("roles", cadastro.getAuthorities())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String token= this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return ResponseEntity.ok(token);
    }
}








