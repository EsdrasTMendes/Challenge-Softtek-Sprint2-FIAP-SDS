package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.RelatorioRisco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRiscoRepository extends MongoRepository<RelatorioRisco, String> {

    List<RelatorioRisco> findAllByEmpresaId(String empresaId);
}