package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Integer> {

    public List<Anotacao> findByUsuarioId(Integer idUsuario);

    public List<Anotacao> findByUsuarioEmail(String email);
}
