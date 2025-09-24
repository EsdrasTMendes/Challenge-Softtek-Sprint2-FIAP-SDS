package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.QuestionarioSemanal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionarioSemanalRepository  extends MongoRepository<QuestionarioSemanal, String>{

}
