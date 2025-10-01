package br.com.softtek.apichallengersds.dto;

import java.util.Map;

public record AuditLogCreateDTO(
        String empresaId,
        String acao,
        Map<String, Object> detalhes
) {}
