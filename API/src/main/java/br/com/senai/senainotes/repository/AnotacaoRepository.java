package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Integer> {
}
