package br.com.softtek.apichallengersds.repository;


import br.com.softtek.apichallengersds.model.Checkin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckinRepository extends MongoRepository<Checkin, String> {
    List<Checkin> findByUserId(Long userId);

    static List<Checkin> findByUsuarioId(String usuarioId) {
        return null;
    }
}