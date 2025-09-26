package br.com.senai.senainotes.service;

import br.com.senai.senainotes.model.Tag;
import br.com.senai.senainotes.repository.TagRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Listar
    public List<Tag> listarTags() {
        return tagRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    // Cadastrar
    public Tag cadastrarTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Buscar
    public Tag buscarTagPorId(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    // Deletar
    public Tag deletarTag(Integer id) {
        Tag tag = buscarTagPorId(id);

        if (tag == null) {
            return null;
        }
        tagRepository.delete(tag);
        return tag;
    }

    // Atualizar
    public Tag atualizaTag(Integer id, Tag tagNova) {
        Tag tagAntiga = buscarTagPorId(id);

        if (tagAntiga == null) {
            return null;
        }
        tagAntiga.setNome(tagNova.getNome());
        return tagRepository.save(tagAntiga);
    }
}
