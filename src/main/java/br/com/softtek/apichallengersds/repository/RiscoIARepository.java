package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.RiscoIA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiscoIARepository extends MongoRepository<RiscoIA, String> {
}