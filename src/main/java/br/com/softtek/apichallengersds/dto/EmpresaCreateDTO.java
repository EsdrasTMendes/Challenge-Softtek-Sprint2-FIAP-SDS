package br.com.softtek.apichallengersds.dto;
import java.util.List;

public record EmpresaCreateDTO(
        String nome,
        String adminUsername,
        String adminPassword,
        List<String> setores
) {
}
