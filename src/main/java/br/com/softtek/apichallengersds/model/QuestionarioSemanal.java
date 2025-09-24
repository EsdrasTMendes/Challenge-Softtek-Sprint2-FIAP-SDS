package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "questionarios_semanais")
public class QuestionarioSemanal {
    @Id
    private String id;

    private String empresaId;
    private String usuarioAnonimoId;
    private String semanaInicio;
    private String versao;
    private RespostaQuestionarios respostas;
    private Date criadoEm;
}
