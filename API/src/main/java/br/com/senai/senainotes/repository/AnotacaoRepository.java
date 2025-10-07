package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Integer> {

    public List<Anotacao> findByUsuarioId(Integer idUsuario);

    // Vamos construir esta query juntos!
    @Query("SELECT DISTINCT a FROM Anotacao a " +
            "LEFT JOIN FETCH a.usuario u " +
            "LEFT JOIN FETCH a.tagAnotacao ta " +
            "LEFT JOIN FETCH ta.idTag t " +
            "WHERE LOWER(u.email) = LOWER(:emailDoUsuario)")
    List<Anotacao> findByUsuarioEmail(@Param("emailDoUsuario") String email);

}
