package br.com.softtek.apichallengersds.model;

import lombok.Data;
import java.util.Map;

@Data
public class Metricas {
    private ContagemRiscos contagemRiscos;
    private Map<String, Object> indicadores; // Objeto genérico
}