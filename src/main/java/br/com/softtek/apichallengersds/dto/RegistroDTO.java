package br.com.softtek.apichallengersds.dto;

public record RegistroDTO (
    String username,
    String password,
    String empresaId,
    String email,
    String doc
    ) {

}
