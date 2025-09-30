package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.LogAuditoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAuditoriaRepository extends MongoRepository<LogAuditoria, String> {
}
