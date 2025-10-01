package br.com.softtek.apichallengersds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "historicos")
public class HistoricoUsuario {
    @Id
    private String id;
    private String usuarioId;
    private LocalDateTime data;
    private String anotacoes;

    // getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getAnotacoes() { return anotacoes; }
    public void setAnotacoes(String anotacoes) { this.anotacoes = anotacoes; }
}
