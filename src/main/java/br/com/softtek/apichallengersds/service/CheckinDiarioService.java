package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.CheckinDiario;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.model.Usuario;
import br.com.softtek.apichallengersds.repository.CheckinDiarioRepository;
import br.com.softtek.apichallengersds.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class CheckinDiarioService {

    @Autowired
    private CheckinDiarioRepository checkinDiarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void saveCheckin(CheckinDiario checkin, Authentication authentication) {
        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByUserAuthId(userAuth.getId())
                .orElseThrow(() -> new RuntimeException("Perfil de usuário de dados não encontrado."));

        checkin.setEmpresaId(usuario.getEmpresaId());
        checkin.setUsuarioAnonimoId(usuario.getUsuarioAnonimoId());
        checkin.setCriadoEm(new Date());

        checkin.setData(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        checkinDiarioRepository.save(checkin);
    }
}