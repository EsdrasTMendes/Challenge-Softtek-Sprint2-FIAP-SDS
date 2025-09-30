package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "papo_particular")
public class PapoParticular {
    @Id
    private String id;

    private String empresaId;
    private String usuarioAnonimoId;
    private TipoPapo tipo;
    private String conteudo;
    private List<String> etiquetas;
    private Moderacao moderacao;
    private Date criadoEm;
}