package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.CheckinDiario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinDiarioRepository extends MongoRepository<CheckinDiario, String> {

}
