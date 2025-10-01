package br.com.softtek.apichallengersds.model;


import lombok.Data;
import java.util.List;

@Data
public class CategoriaQuestionario
{
    private String nome;
    private List<Pergunta> perguntas;
}
