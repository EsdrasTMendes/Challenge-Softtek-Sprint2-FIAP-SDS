package br.com.softtek.apichallengersds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "canais_apoio")
public class CanaisApoio {

    @Id
    private String id;
    private String nomeCanal;
    private String tipo; // Ex: telefone, chat, email, presencial
    private String contato; // Número, link, email, endereço
    private String descricao;
    private String recomendacao; // Quando usar, ação recomendada

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNomeCanal() { return nomeCanal; }
    public void setNomeCanal(String nomeCanal) { this.nomeCanal = nomeCanal; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRecomendacao() { return recomendacao; }
    public void setRecomendacao(String recomendacao) { this.recomendacao = recomendacao; }
}
