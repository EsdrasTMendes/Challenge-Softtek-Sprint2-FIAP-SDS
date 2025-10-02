package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.model.HistoricoUsuario;
import br.com.softtek.apichallengersds.service.HistoricoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
public class HistoricoUsuarioController {

    @Autowired
    private HistoricoUsuarioService service;

    @GetMapping("/{usuarioId}")
    public List<HistoricoUsuario> getHistoricoUsuario(@PathVariable String usuarioId) {
        return service.buscarPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<HistoricoUsuario> criar(@RequestBody HistoricoUsuario historico) {
        HistoricoUsuario criado = service.salvar(historico);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoUsuario> atualizar(@PathVariable String id, @RequestBody HistoricoUsuario historico) {
        HistoricoUsuario atualizado = service.atualizar(id, historico);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

