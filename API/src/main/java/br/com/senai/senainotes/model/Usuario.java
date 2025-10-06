package br.com.senai.senainotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "senainotes")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "senha", nullable = false, length = Integer.MAX_VALUE)
    private String senha;

    @Column(name = "flag_dark_mode")
    private Boolean flagDarkMode;

    @Column(name = "data_cadastro", nullable = false)
    private OffsetDateTime dataCadastro;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public String getPassword() {return senha;}

    @Override
    @JsonIgnore
    public String getUsername() {return email;}

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {return true;}

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {return true;}

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    @JsonIgnore
    public boolean isEnabled() {return true;}

}