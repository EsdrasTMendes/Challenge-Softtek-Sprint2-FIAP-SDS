package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.CanaisApoio;
import br.com.softtek.apichallengersds.repository.CanaisApoioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CanaisApoioService {

    @Autowired
    private CanaisApoioRepository repository;

    public List<CanaisApoio> listarTodos() {
        return repository.findAll();
    }

    public CanaisApoio salvar(CanaisApoio canal) {
        return repository.save(canal);
    }

    public CanaisApoio atualizar(String id, CanaisApoio canal) {
        Optional<CanaisApoio> existente = repository.findById(id);
        if (!existente.isPresent()) {
            throw new RuntimeException("Canal não encontrado");
        }
        canal.setId(id);
        return repository.save(canal);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }
}
