package br.com.softtek.apichallengersds.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "questionario_modelos")
public class QuestionarioModelo {
    @Id
    private String id;
    private String versao;
    private String nome;
    private boolean ativo;
    private List<CategoriaQuestionario> categorias;
}
