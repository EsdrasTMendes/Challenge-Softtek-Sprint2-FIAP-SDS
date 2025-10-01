package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.Checkin;
import br.com.softtek.apichallengersds.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository repository;

    public Checkin salvar(Checkin checkin) {
        checkin.setDataRegistro(LocalDate.now());
        return repository.save(checkin);
    }

    public List<Checkin> listarPorUsuario(Long userId) {
        return repository.findByUserId(userId);
    }
}
