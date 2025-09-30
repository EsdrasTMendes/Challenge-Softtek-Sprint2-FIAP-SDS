package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.dto.AdminCreateDTO;
import br.com.softtek.apichallengersds.service.UserAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import br.com.softtek.apichallengersds.dto.EmpresaCreateDTO;
import br.com.softtek.apichallengersds.dto.EmpresaCreateResponseDTO;
import br.com.softtek.apichallengersds.model.Empresa;
import br.com.softtek.apichallengersds.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController

@RequestMapping("/api/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<EmpresaCreateResponseDTO> createEmpresa(@RequestBody EmpresaCreateDTO empresaCreateDTO) {
        Empresa empresaCriada = empresaService.createEmpresaAndFirstAdmin(empresaCreateDTO);

        EmpresaCreateResponseDTO response = new EmpresaCreateResponseDTO(
                "SUCESSO",
                "Empresa registrada com sucesso!",
                empresaCriada.getEmpresaId(),
                empresaCriada.getRegistrationLink()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{empresaId}/admins")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<String> createCompanyAdmin (
            @PathVariable String empresaId,
            @RequestBody AdminCreateDTO adminCreateDTO,
            Authentication authentication
    ) {
        userAuthService.createCompanyAdmin(adminCreateDTO, empresaId, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body("Company Admin criado com sucesso!");
    }


    @GetMapping
    public ResponseEntity<List<Empresa>> listarTodasEmpresas() {
        List<Empresa> empresas = empresaService.listaTodas();
        return ResponseEntity.ok(empresas);
    }
}
