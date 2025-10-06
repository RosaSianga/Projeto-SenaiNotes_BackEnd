package br.com.senai.senainotes.controller;

import br.com.senai.senainotes.model.Nota;
import br.com.senai.senainotes.service.NotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService service) {
        this.notaService = service;
    }

    public ResponseEntity<Nota> cadastrarNota(Nota nota) {
        Nota novaNota = notaService.cadastrarNota(nota);
        if (novaNota == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(novaNota);
    }
}
