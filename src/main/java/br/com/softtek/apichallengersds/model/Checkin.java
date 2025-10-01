package br.com.softtek.apichallengersds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "checkins")
public class Checkin {

    @Id
    private String id;

    private Long userId;
    private String humor;
    private String sono;
    private String concentracao;
    private String energia;
    private LocalDate dataRegistro;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getHumor() { return humor; }
    public void setHumor(String humor) { this.humor = humor; }

    public String getSono() { return sono; }
    public void setSono(String sono) { this.sono = sono; }

    public String getConcentracao() { return concentracao; }
    public void setConcentracao(String concentracao) { this.concentracao = concentracao; }

    public String getEnergia() { return energia; }
    public void setEnergia(String energia) { this.energia = energia; }

    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }

    public static boolean getData() {
        return false;
    }

    public void setData(LocalDateTime now) {
    }
}