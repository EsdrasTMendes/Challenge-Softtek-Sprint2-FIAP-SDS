package br.com.softtek.apichallengersds.dto;

public record EmpresaCreateResponseDTO(
        String status,
        String message,
        String empresaId,
        String registrationLink
) {
}
