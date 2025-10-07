package br.com.senai.senainotes.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;


import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private String secret = "}]0Gnq|maxtXoU4$KI-,_aL5g5BCmJ]K$/YXlQv7QJtk6d.VD,";


    //1. GERADOR DE TOKENS
    @Bean
    public JwtEncoder jwtEncoder() {

        var secretKey = new SecretKeySpec(secret.getBytes(), "AES");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    //2. VERIFICADOR DE TOKENS
    @Bean
    public JwtDecoder jwtDecoder() {

        var secretKey = new SecretKeySpec(secret.getBytes(), "AES");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    // 3. CRIPTOGRAFAR AS SENHAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 4. GERENTE DE AUTENTICAÇÃO
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    //FILTRO DE SEGURANÇA
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/cadastro/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/usuario/**").permitAll()
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/api/anotacao/**").permitAll()
                        .requestMatchers("/api/tags/**").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
