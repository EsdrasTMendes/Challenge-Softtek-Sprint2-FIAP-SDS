package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.PapoParticular;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapoParticularRepository extends MongoRepository<PapoParticular, String> {
}
