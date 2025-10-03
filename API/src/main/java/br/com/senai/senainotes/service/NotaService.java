package br.com.senai.senainotes.service;

import br.com.senai.senainotes.model.Nota;
import br.com.senai.senainotes.repository.NotaRepository;
import org.springframework.stereotype.Service;

@Service
public class NotaService {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository repo) {
        this.notaRepository = repo;
    }

    public Nota cadastrarNota(Nota nota) {
        return notaRepository.save(nota);
    }
}
