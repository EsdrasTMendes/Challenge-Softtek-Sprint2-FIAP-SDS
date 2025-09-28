package br.com.softtek.apichallengersds.service;
import br.com.softtek.apichallengersds.model.Empresa;
import br.com.softtek.apichallengersds.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.softtek.apichallengersds.dto.EmpresaCreateDTO;
import br.com.softtek.apichallengersds.model.Empresa;
import br.com.softtek.apichallengersds.service.UserAuthService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Date;
import java.util.UUID;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserAuthService userAuthService;

    public List<Empresa> listaTodas() {
        return empresaRepository.findAll();
    }

    public Empresa createEmpresaAndFirstAdmin(EmpresaCreateDTO empresaCreateDTO) {
        Empresa novaEmpresa = new Empresa();
        novaEmpresa.setNome(empresaCreateDTO.nome());
        novaEmpresa.setSetores(empresaCreateDTO.setores());
        String idGerado = "emp-" + UUID.randomUUID().toString();
        novaEmpresa.setEmpresaId(idGerado);
        novaEmpresa.setCriadoEm(new Date());

        Empresa empresaSalva = empresaRepository.save(novaEmpresa);

        String registrationLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("api/auth/register/{empresaId}/user")
                        .buildAndExpand(empresaSalva.getEmpresaId())
                        .toUriString();
        empresaSalva.setRegistrationLink(registrationLink);
        empresaRepository.save(empresaSalva);

        userAuthService.createUser(
                empresaCreateDTO.adminUsername(),
                empresaCreateDTO.adminPassword(),
                empresaSalva.getEmpresaId(),
                List.of("ROLE_COMPANY_ADMIN")
        );

        return empresaSalva;
    }
}
