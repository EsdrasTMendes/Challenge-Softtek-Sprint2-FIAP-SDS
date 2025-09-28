package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
@Data
@Document(collection = "empresas")
public class Empresa {
    @Id
    private String id;
    private String empresaId;
    private String nome;
    private List<String> setores;
    private Date criadoEm;
    private Lgpd lgpd;
    private String registrationLink;
}
