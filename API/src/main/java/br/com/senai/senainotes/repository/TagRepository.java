package br.com.senai.senainotes.repository;

import br.com.senai.senainotes.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    public Tag findByNome(String nome);

}
