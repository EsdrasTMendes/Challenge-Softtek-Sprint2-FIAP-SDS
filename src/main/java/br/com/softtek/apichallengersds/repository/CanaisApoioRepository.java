package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.CanaisApoio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CanaisApoioRepository extends MongoRepository<CanaisApoio, String> {
}
