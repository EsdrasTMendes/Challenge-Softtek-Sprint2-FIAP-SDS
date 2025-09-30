package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.ExportacaoRelatorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportacaoRelatorioRepository extends MongoRepository<ExportacaoRelatorio, String> {
}