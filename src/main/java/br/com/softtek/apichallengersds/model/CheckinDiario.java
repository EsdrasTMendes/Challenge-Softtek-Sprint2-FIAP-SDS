package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "checkins_diarios")
public class CheckinDiario {
    @Id
    private String id;

    private String empresaId;
    private String usuarioAnonimoId;
    private String data; // Formato YYYY-MM-DD
    private RespostasCheckin respostas;
    private String observacoes;
    private Date criadoEm;
}
