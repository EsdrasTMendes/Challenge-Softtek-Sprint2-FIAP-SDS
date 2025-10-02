package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "relatorios_risco")
public class RelatorioRisco {
    @Id
    private String id;
    private String empresaId;
    private String usuarioAnonimoId;
    private String semanaReferencia;
    private Date dataAnalise;

    private Map<String, String> diagnosticos;
}