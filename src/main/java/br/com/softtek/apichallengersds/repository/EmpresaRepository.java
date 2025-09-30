package br.com.softtek.apichallengersds.repository;

import br.com.softtek.apichallengersds.model.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends MongoRepository<Empresa, String>{
    Optional<Empresa> findByEmpresaId(String empresaId);
}
