package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUserAuthId(String userAuthId);
}

