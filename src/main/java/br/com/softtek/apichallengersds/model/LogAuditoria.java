package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "logs_auditoria")
public class LogAuditoria {
    @Id
    private String id;

    private String empresaId;
    private String acao;
    private Map<String, Object> detalhes; // Objeto genérico
    private Date criadoEm;
}