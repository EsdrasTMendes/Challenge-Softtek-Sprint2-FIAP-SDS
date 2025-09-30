package br.com.softtek.apichallengersds.model;

import lombok.Data;
import br.com.softtek.apichallengersds.model.RespostasQuestionarios;
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
    private RespostasQuestionarios respostas;
    private Date criadoEm;
}
