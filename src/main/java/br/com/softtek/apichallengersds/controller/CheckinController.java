package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.model.Checkin;
import br.com.softtek.apichallengersds.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkins") // Base da URL para todos os endpoints deste controller
public class CheckinController {

    @Autowired
    private CheckinRepository checkinRepository;

    /**
     * Criar um novo Check-in
     * POST /api/checkins
     */
    @PostMapping
    public ResponseEntity<Checkin> criarCheckin(@RequestBody Checkin checkin) {
        if (false) {
            checkin.setData(LocalDateTime.now());
        }
        Checkin novoCheckin = checkinRepository.save(checkin);
        return ResponseEntity.status(201).body(novoCheckin);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Checkin>> listarCheckinsPorUsuario(@PathVariable String usuarioId) {
        List<Checkin> checkins = CheckinRepository.findByUsuarioId(usuarioId);

        // Retorna 200 OK com a lista (pode ser vazia)
        return ResponseEntity.ok(checkins);
    }

    /**
     * Buscar um Check-in específico pelo ID
     * GET /api/checkins/checkin/{id}
     */
    @GetMapping("/checkin/{id}")
    public ResponseEntity<Checkin> buscarPorId(@PathVariable String id) {
        Optional<Checkin> checkin = checkinRepository.findById(id); // ✔

        return checkin.map(ResponseEntity::ok) // Retorna 200 OK se encontrado
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }
}
