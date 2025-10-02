package br.com.softtek.apichallengersds.service;


import br.com.softtek.apichallengersds.model.HistoricoUsuario;
import br.com.softtek.apichallengersds.repository.HistoricoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioRepository repository;

    public List<HistoricoUsuario> buscarPorUsuario(String usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public HistoricoUsuario salvar(HistoricoUsuario historico) {
        return repository.save(historico);
    }

    public HistoricoUsuario atualizar(String id, HistoricoUsuario historico) {
        Optional<HistoricoUsuario> existente = repository.findById(id);
        if (!((Optional<?>) existente).isPresent()) {
            throw new RuntimeException("Histórico não encontrado");
        }
        historico.setId(id);
        return repository.save(historico);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }
}
