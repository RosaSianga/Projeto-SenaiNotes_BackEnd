package br.com.senai.senainotes.service;

import br.com.senai.senainotes.dto.TagListagemDTO;
import br.com.senai.senainotes.model.Tag;
import br.com.senai.senainotes.repository.TagRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }



    // Listar
    public List<TagListagemDTO> listarTags() {
        List<Tag> tags = tagRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return tags.stream()
                .map(this::converterParaListagemDto)
                .collect(Collectors.toList());
    }

    // Convertendo para Dto
    private TagListagemDTO converterParaListagemDto(Tag tag) {
        TagListagemDTO dto = new TagListagemDTO();

        dto.setId(tag.getId());
        dto.setNome(tag.getNome());
        return dto;
    }

    // Cadastrar
    public Tag cadastrarTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Buscar tag por ID
    public TagListagemDTO buscarTagPorId(Integer id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        return converterParaListagemDto(tag);
    }

    // Buscar
    public  Tag buscarPorId(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    // Deletar
    public Tag deletarTag(Integer id) {
        Tag tag = buscarPorId(id);

        if (tag == null) {
            return null;
        }
        tagRepository.delete(tag);
        return tag;
    }

    // Atualizar
    public Tag atualizaTag(Integer id, Tag tagNova) {
        Tag tagAntiga = buscarPorId(id);

        if (tagAntiga == null) {
            return null;
        }
        tagAntiga.setNome(tagNova.getNome());
        return tagRepository.save(tagAntiga);
    }
}
