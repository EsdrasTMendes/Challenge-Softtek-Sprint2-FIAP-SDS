package br.com.softtek.apichallengersds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta {
    private String id;
    private String texto;
    private String tipo;
}