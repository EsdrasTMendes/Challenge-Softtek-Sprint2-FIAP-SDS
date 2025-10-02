package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.HistoricoUsuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoricoUsuarioRepository extends MongoRepository<HistoricoUsuario, String> {
    List<HistoricoUsuario> findByUsuarioId(String usuarioId);
}
