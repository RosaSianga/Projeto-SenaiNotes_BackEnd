package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRepository extends JpaRepository<Nota,Integer> {
}
