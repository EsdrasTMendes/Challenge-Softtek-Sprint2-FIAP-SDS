package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "riscos_ia")
public class RiscoIA {
    @Id
    private String id;

    private String empresaId;
    private Periodo periodo;
    private EscopoRisco escopo;
    private Metricas metricas;
    private String versaoModelo;
    private Date geradoEm;
}