package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.model.CanaisApoio;
import br.com.softtek.apichallengersds.service.CanaisApoioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canais-apoio")
public class CanaisApoioController {

    @Autowired
    private CanaisApoioService service;

    @GetMapping
    public List<CanaisApoio> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<CanaisApoio> criar(@RequestBody CanaisApoio canal) {
        CanaisApoio criado = service.salvar(canal);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanaisApoio> atualizar(@PathVariable String id, @RequestBody CanaisApoio canal) {
        CanaisApoio atualizado = service.atualizar(id, canal);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
