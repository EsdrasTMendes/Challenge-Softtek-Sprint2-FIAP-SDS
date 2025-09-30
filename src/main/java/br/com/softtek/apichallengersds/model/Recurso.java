package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "recursos")
public class Recurso {
    @Id
    private String id;

    private String empresaId;
    private String titulo;
    private TipoRecurso tipo;
    private String url;
    private List<String> etiquetas;
    private Date publicadoEm;
}