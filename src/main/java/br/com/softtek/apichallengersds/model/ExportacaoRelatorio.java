package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "exportacoes_relatorios")
public class ExportacaoRelatorio {
    @Id
    private String id;

    private String empresaId;
    private String perfilAtor;
    private String acao;
    private Map<String, Object> escopo; // Objeto genérico
    private Date criadoEm;
}
