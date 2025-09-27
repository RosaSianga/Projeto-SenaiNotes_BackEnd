package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.dto.AnotacaoListagemDTO;
import br.com.senai.senainotes.model.Anotacao;
import br.com.senai.senainotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Integer> {

    public List<Anotacao> findByUsuarioId(Integer idUsuario);
}
