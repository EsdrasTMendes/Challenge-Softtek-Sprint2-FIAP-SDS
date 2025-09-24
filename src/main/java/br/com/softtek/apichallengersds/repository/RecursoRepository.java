package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.Recurso;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoRepository extends MongoRepository<Recurso, String> {
}
