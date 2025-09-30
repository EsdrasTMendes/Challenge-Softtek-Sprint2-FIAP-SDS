package br.com.softtek.apichallengersds.model;

import lombok.Data;
import java.util.Date;

@Data
public class Meta {
    private Date criadoEm;
    private Date ultimaAtividadeEm;
    private String plataforma;
}
