package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.QuestionarioModelo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionarioModeloRepository extends MongoRepository<QuestionarioModelo, String> {
    Optional<QuestionarioModelo> findByAtivo(boolean ativo);
}