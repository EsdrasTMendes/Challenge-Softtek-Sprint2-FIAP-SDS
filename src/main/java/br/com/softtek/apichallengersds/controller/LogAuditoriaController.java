package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.dto.AuditLogCreateDTO;
import br.com.softtek.apichallengersds.model.LogAuditoria;
import br.com.softtek.apichallengersds.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogAuditoriaController {

    @Autowired
    private AuditLogService auditLogService;

    /** Cria um registro de auditoria genérico.
     * Ações sugeridas: SUBMISSAO_AVALIACAO, EDICAO_DADOS, ALERTA
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<LogAuditoria> criar(@RequestBody AuditLogCreateDTO dto) {
        LogAuditoria salvo = auditLogService.log(dto.empresaId(), dto.acao(), dto.detalhes());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /** Lista registros com filtros opcionais por empresaId e ação */
    @GetMapping
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','COMPANY_ADMIN')")
    public ResponseEntity<List<LogAuditoria>> listar(
            @RequestParam(required = false) String empresaId,
            @RequestParam(required = false) String acao
    ) {
        return ResponseEntity.ok(auditLogService.listar(empresaId, acao));
    }
}
