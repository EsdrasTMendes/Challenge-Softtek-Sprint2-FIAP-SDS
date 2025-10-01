package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.model.RelatorioRisco;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.repository.RelatorioRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRiscoRepository relatorioRiscoRepository;

    @GetMapping("/empresa")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<List<RelatorioRisco>> getRelatoriosDaEmpresa(Authentication authentication) {
        UserAuth companyAdmin = (UserAuth) authentication.getPrincipal();
        String empresaId = companyAdmin.getEmpresaId();

        List<RelatorioRisco> relatorios = relatorioRiscoRepository.findAllByEmpresaId(empresaId);

        return ResponseEntity.ok(relatorios);
    }
}